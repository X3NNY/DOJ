package pers.dreamer.oj.judge.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.dreamer.oj.judge.dto.CodeDto;
import pers.dreamer.oj.judge.pojo.*;
import pers.dreamer.oj.judge.service.*;
import pers.dreamer.oj.judge.util.*;
import pers.dreamer.oj.judge.util.idworker.Sid;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@EnableAsync
@Slf4j
public class HackController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Resource
    ProblemService problemService;

    @Resource
    SubmitlistService submitlistService;

    @Resource
    HackService hackService;

    @Resource
    Sid ssid;

    @Resource
    ApplicationContext applicationContext;

    @Autowired
    MessageService messageService;

    @Resource
    UserService userService;

    @Value("${PROBLEM_PATH}")
    private String PROBLEM_PATH;

    @Autowired
    RedisTemplate<String,String> stringRedisConnection;

    @PostMapping("/api/hack/new/file")
    public Map<String, Object> uploadFileData(@RequestParam("datafile") MultipartFile data,@RequestParam("sid") Integer sid,@RequestParam("token") String token) throws IOException {
        Map<String,Object> ret = new HashMap<>();
        Submitlist submitlist = submitlistService.findSubmitlistBySid(sid);
        String uid = (String) stringRedisConnection.opsForHash().get(token, token);
        if(uid == null || uid.equals("")) {
            ret.put("state",1);
        }
        User user = userService.findUser(Integer.parseInt(uid));
        if (submitlist == null || user == null || !submitlistService.findSubmitlistOnAcByCidAndUidAndPid(-1, user.getUid(), submitlist.getPid())) {
            ret.put("state",1);
        } else {
            String path = PROBLEM_PATH+"/tmp/";
            File file = new File(path + "ext_" + user.getUsername() + "_" + ssid + ".in");
            if(!file.getParentFile().exists()) file.mkdirs();
            ret.put("state", 0);
            data.transferTo(file);

            Hack hack = new Hack();
            hack.setDate(new Date());
            hack.setUid(user.getUid());
            hack.setSid(submitlist.getSid());
            hack.setFilename(file.getName());
            hackService.insertHack(hack);

            hackJudge(hackService.findHackByFilename(hack.getFilename()),user);
        }
        return ret;
    }

    public void hackJudge(Hack hack,User user) throws IOException {
        if (hack != null) {
            Submitlist submitlist = submitlistService.findSubmitlistBySid(hack.getSid());
            Problem problem = problemService.findProblemByPid(submitlist.getPid());
            if (check(problem,hack.getFilename())) {
                String dockerID = createDocker();
                System.out.println(dockerID);
                try {
                    if (execStd(problem, hack.getFilename(), dockerID)) {
                        LinuxOptUtil.moveData(PROBLEM_PATH+"/tmp/"+hack.getFilename(),PROBLEM_PATH+"/"+problem.getPid()+"/");
                        moveData(dockerID, problem.getPid(), hack.getFilename().replace(".in", ".out"));

                        boolean res = judgeHack(problem, submitlist, hack);
                        File file1 = new File(PROBLEM_PATH+"/" + problem.getPid() + "/" + hack.getFilename()),
                                file2 = new File(PROBLEM_PATH+"/" + problem.getPid() + "/" + hack.getFilename().replace(".in", ".out"));
                        if (res) {
                            if (file1.exists()) file1.delete();
                            if (file2.exists()) file2.delete();
                            hack.setResult(1);
                        } else {
                            user.setLevel(user.getLevel()+50);
                            user.setLiveness(user.getLiveness()+50);
                            Message message = new Message();
                            message.setTitle("成功贡献一条Hack数据积分奖励");
                            message.setContent("获得DC： +50 DC，现在共有"+(user.getCoins()+50)+"DC，继续保持噢！");
                            message.setStatus(0);
                            message.setTargetuid(user.getUid());
                            message.setSenduid(-1);
                            message.setDate(new Date());
                            messageService.insertMessage(message);
                            user.setCoins(user.getCoins()+50);
                            userService.updateUserInfo(user);
                            hack.setResult(2);
                            reJudgeAll(problem);
                        }
                    } else {
                        hack.setResult(-1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                destroyDocker(dockerID);
            } else {
                hack.setResult(1);
                File file = new File(PROBLEM_PATH+"/tmp/"+hack.getFilename());
                if (file.exists()) file.delete();
            }
            hackService.update(hack);
        }
    }

    @Async
    public void reJudgeAll(Problem problem) {
        List<Submitlist> submitlists = submitlistService.findSubmitlistsByCidAndPidAndResult(-1,problem.getPid(),0);

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        for (Submitlist submitlist:submitlists) {

            submitlist.setTimeUsed(null);
            submitlist.setMemoryUsed(null);
            submitlist.setResult(7);
            submitlistService.updateSubmitlist(submitlist);

            CodeDto codeDto = new CodeDto();
            codeDto.setPid(problem.getPid());
            codeDto.setSid(submitlist.getSid());
            codeDto.setTimeLimit(problem.getTimelimit());
            codeDto.setMemoryLimit(problem.getMemorylimit());
            codeDto.setKind(problem.getType());
            codeDto.setSpecial(problem.getSpecial() != 0);
            codeDto.setLang(submitlist.getLang());
            codeDto.setCode(null);
            rabbitTemplate.convertAndSend("oj.direct.judge", "oj.judge", codeDto);
        }
    }

    private void destroyDocker(String dockerID) throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("rm");
        cmd.add("-f");
        cmd.add(dockerID);

        new ProcessBuilder(cmd).start();
    }

    private boolean judgeHack(Problem problem, Submitlist submitlist,Hack hack) throws IOException {
        CodeDto codeDto = new CodeDto();
        codeDto.setMemoryLimit(problem.getMemorylimit());
        codeDto.setTimeLimit(problem.getTimelimit());
        codeDto.setKind(problem.getType());
        codeDto.setCode(problemService.findCodeBySid(submitlist.getSid()).getText());
        codeDto.setLang(submitlist.getLang());
        codeDto.setSpecial(problem.getSpecial() != 0);
        codeDto.setSid(submitlist.getSid());
        codeDto.setPid(problem.getPid());

        boolean res = false;

        switch (problem.getType()) {
            case 0:res = execHack(codeDto,hack);break;
            case 1:res = execHack1(codeDto,hack);break;
            case 2:res = execHack2(codeDto,hack);break;
            case 3:res = execHack3(codeDto,hack);break;
        }

        return res;
    }

    private void moveData(String dockerID,Integer pid, String filename) throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("cp");
        cmd.add(String.format("%s:/%s",dockerID,filename));
        cmd.add(PROBLEM_PATH+"/"+pid+"/");

        new ProcessBuilder(cmd).start();
    }

    private boolean execStd(Problem problem, String filename, String dockerID) throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("cp");
        cmd.add(PROBLEM_PATH+"/"+problem.getPid()+"/");
        cmd.add(String.format("%s:/", dockerID));
        new ProcessBuilder(cmd).start();

        cmd.clear();
        cmd.add("docker");
        cmd.add("cp");
        cmd.add(PROBLEM_PATH+"/tmp/"+filename);
        cmd.add(String.format("%s:/", dockerID));
        new ProcessBuilder(cmd).start();

        cmd.clear();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerID);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add(String.format("mv /%d/* /",problem.getPid()));
        new ProcessBuilder(cmd).start();

        cmd.clear();
        cmd.add("docker");
        cmd.add("exec");
        cmd.add("-i");
        cmd.add(dockerID);
        cmd.add("/bin/bash");
        cmd.add("-c");
        cmd.add(String.format("python3 /judge.py \'/std\' \'/%s\' \'/%s\' %d %d %d %d", filename, filename.replace(".in", ".out"),
                problem.getTimelimit(), problem.getMemorylimit() * 1024,problem.getType(),problem.getSpecial()));

        Process process = new ProcessBuilder(cmd).start();

        String res = "";
        byte[] bytes = new byte[1024];
        InputStream inputStream = process.getInputStream();
        if (inputStream.read(bytes) != -1) {
            res = new String(bytes, StandardCharsets.UTF_8);
        }
        res = res.replace("}", String.format(", \"id\": %d}", 0)).trim();
        SimpleResult simpleResult = Judge.gson.fromJson(res,SimpleResult.class);
        System.out.println(simpleResult);
        return simpleResult.getResult()==0;
    }

    private String createDocker() throws IOException {
        List<String> cmd = new ArrayList<>();
        cmd.add("docker");
        cmd.add("run");
        cmd.add("-d");
        cmd.add("-t");
        cmd.add("doj");
        System.out.println(cmd);
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        Process process = processBuilder.start();

        String dockerId = "";
        byte[] bytes = new byte[1024];
        InputStream inputStream = process.getInputStream();
        if (inputStream.read(bytes) != -1) {
            dockerId = new String(bytes, StandardCharsets.UTF_8);
        }
        return dockerId.trim();
    }

    private boolean check(Problem problem, String filename) {
        List<String> cmd = new ArrayList<>();
        cmd.add(PROBLEM_PATH+"/"+problem.getPid()+"/valid");
        cmd.add(PROBLEM_PATH+"/tmp/"+filename);
        try {
            Process process = new ProcessBuilder(cmd).start();

            InputStream in = process.getInputStream();
            byte[] bytes = new byte[1024];
            in.read(bytes);
            String res = new String(bytes,StandardCharsets.UTF_8).trim();
            if ("0".equals(res)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private boolean execHack3(CodeDto codeDto,Hack hack) {
        return false;
    }

    private boolean execHack2(CodeDto codeDto,Hack hack) throws IOException {
        JudgeTask task = new JudgeTask();
        Judge judge;
        switch (task.getLang()) {
            case 0: judge = applicationContext.getBean("icJudge", ICJudge.class);
                break;
            case 1: judge = applicationContext.getBean("icppJudge", ICPPJudge.class);
                break;
            case 2: judge = applicationContext.getBean("ijavaJudge",IJavaJudge.class);
                task.setMemoryLimit(128);
                task.setRMemoryLimit(codeDto.getMemoryLimit());
                break;
            case 3: judge = applicationContext.getBean("ipyJudge",IPythonJudge.class);
                break;
            default:
                return false;
        }
        return setTaskAndJudge(codeDto, hack, task, judge);
    }

    private boolean execHack1(CodeDto codeDto,Hack hack) throws IOException {
        JudgeTask task = new JudgeTask();
        Judge judge;
        switch (task.getLang()){
            case 0: judge = applicationContext.getBean("acJudge",ACJudge.class);
                break;
            case 1: judge = applicationContext.getBean("acppJudge",ACPPJudge.class);
                break;
            case 2: judge = applicationContext.getBean("ajavaJudge",AJavaJudge.class);
                task.setMemoryLimit(300);
                task.setRMemoryLimit(codeDto.getMemoryLimit());
                break;
            case 3: judge = applicationContext.getBean("apyJudge",APythonJudge.class);
                break;
            default:
                return false;
        }
        return setTaskAndJudge(codeDto, hack, task, judge);
    }

    private boolean setTaskAndJudge(CodeDto codeDto, Hack hack, JudgeTask task, Judge judge) throws IOException {
        JudgeResult judgeResult = new JudgeResult();
        task.setPid(codeDto.getPid());
        task.setTimeLimit(codeDto.getTimeLimit());
        task.setMemoryLimit(codeDto.getMemoryLimit());
        task.setCode(codeDto.getCode());
        task.setSpecial(codeDto.getSpecial());
        task.setKind(codeDto.getKind());
        task.setExecCmd(LangsUtil.execCmds.get(codeDto.getLang()));
        judge.th = new ThreadPoolExecutor(judge.initcores,judge.maxcores,judge.keepAliveTime, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(),new ThreadPoolExecutor.DiscardOldestPolicy());
        judge.setTask(task);
        return runExec(judgeResult, judge,hack);
    }

    private boolean execHack(CodeDto codeDto,Hack hack) throws IOException {
        JudgeTask task = new JudgeTask();
        Judge judge;
        switch (task.getLang()){
            case 0: judge = applicationContext.getBean("cJudge",CJudge.class);
                break;
            case 1: judge = applicationContext.getBean("cppJudge",CPPJudge.class);
                break;
            case 2: judge = applicationContext.getBean("javaJudge",JavaJudge.class);
                task.setMemoryLimit(300);
                task.setRMemoryLimit(codeDto.getMemoryLimit());
                break;
            case 3: judge = applicationContext.getBean("pyJudge",PythonJudge.class);
                break;
            default:
                return false;
        }
        return setTaskAndJudge(codeDto, hack, task, judge);
    }

    private boolean runExec(JudgeResult judgeResult, Judge judge,Hack hack) throws IOException {
        String dockerId = judge.create();
        try {
            judge.setDockerId(dockerId);
            judge.build();
            String msg = judge.complier();
            System.out.println(msg);
            if (msg.contains("error")) {
                judgeResult.setRes(9);
                judgeResult.setMsg(msg);
            } else {
                judgeResult = judge.exec();
            }
        } catch (Exception e) {
            e.printStackTrace();
            judgeResult.setRes(8);
        }

        moveData(dockerId,judge.getTask().getPid(),hack.getFilename().replace(".in",".ans"));

        File file = new File(PROBLEM_PATH+"/"+judge.getTask().getPid()+"/"+hack.getFilename().replace(".in",".ans"));
        String data = NioUtil.readNIO(file.getAbsolutePath());
        hack.setData(data.length()>=252?(data.substring(0,253)+"..."):data);
        file.delete();

        judge.destroy();
        System.out.println(judgeResult);

        return judgeResult.getRes() == 0;
    }
}
