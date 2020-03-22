package pers.dreamer.oj.judge.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DefaultStringRedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.dreamer.oj.judge.service.ProblemService;
import pers.dreamer.oj.judge.service.ProbleminfoService;
import pers.dreamer.oj.judge.service.UserService;
import pers.dreamer.oj.judge.util.idworker.Sid;
import pers.dreamer.oj.judge.pojo.Problem;
import pers.dreamer.oj.judge.pojo.User;
import pers.dreamer.oj.judge.util.JSONUtil;
import pers.dreamer.oj.judge.util.LinuxOptUtil;
import pers.dreamer.oj.judge.util.NioUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

import static java.lang.Math.max;

@RestController
@CrossOrigin
@EnableAsync
public class ProblemController {

    @Autowired
    ProblemService problemService;
    
    @Resource
    UserService userService;

    @Resource
    ProbleminfoService probleminfoService;

    @Autowired
    Sid sid;

    @Autowired
    Gson gson;

    @Autowired
    RedisTemplate<String,String> stringRedisConnection;

    @GetMapping("/api/problem/down/{pid}")
    public Map<String,Object> getProbelmDown(@PathVariable("pid") Integer pid,String token) {
        Map<String, Object> ret = new HashMap<>();
        String user = (String) stringRedisConnection.opsForHash().get(token, token);
        if (user == null || user.equals("")) ret.put("state",1);
        else {
            List<Map<String,Object>> list = new ArrayList<>();
            ret.put("state",0);
            File files = new File("/var/oj/problem/"+pid);
            if (files.isDirectory()) {
                for (File file: Objects.requireNonNull(files.listFiles())) {
                    if (file.getName().indexOf('.') == -1) continue;
                    Map<String,Object> tmp = new HashMap<>();
                    tmp.put("name",file.getName());
                    tmp.put("link","http://120.79.31.49:11000/judge/api/problem/data/"+pid+"?name="+file.getName());
                    tmp.put("size",file.length());
                    list.add(tmp);
                }
            }
            list.sort(Comparator.comparing(o -> ((String) o.get("name"))));
            ret.put("data",list);
        }
        return ret;
    }

    @GetMapping("/api/problem/data/{pid}")
    public void getProblemData(@PathVariable("pid") Integer pid, String name,String token,HttpServletResponse response) throws IOException {
        String user = (String) stringRedisConnection.opsForHash().get(token,token);//(String) redisTemplate.opsForHash().get(token, token);
        if (user == null || user.equals("")) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<html><body><script type='text/javascript'>alert('请先登录！');</script></body></html>");
            response.getWriter().close();
            return ;
        }
        File file = new File("/var/oj/problem/"+pid+"/"+name);
        if (!file.exists()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print("<html><body><script type='text/javascript'>alert('文件不存在');</script></body></html>");
            response.getWriter().close();
            return ;
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        response.setContentType("multipart/form-data");
        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer))>0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
    }

    @GetMapping("/api/problem/sdata/{pid}")
    public Map<String,String> getProblemDataString(@PathVariable("pid")Integer pid, String name,String token) {
        Map<String,String> ret = new HashMap<>();
        String user = (String) stringRedisConnection.opsForHash().get(token,token);
        if (user == null || "".equals(user)) {
            ret.put("data","");
            return ret;
        }
        File file = new File("/var/oj/problem/"+pid+"/"+name);
        if (!file.exists()) {
            ret.put("data","");
            return ret;
        }
        ret.put("data",NioUtil.readNIO(file.getAbsolutePath()));
        return ret;
    }


    @PostMapping("/api/problem/add/data")
    public Map<String,Object> addDatasForProblemByPid(@RequestParam("pid") Integer pid, @RequestParam("datafile") MultipartFile[] files,String token) throws IOException {
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        String uid = (String) stringRedisConnection.opsForHash().get(token,token);
        if(uid == null || uid.equals("")) ret.put("state",1);
        else {
            System.out.println(uid);
            User user = userService.findUser(Integer.parseInt(uid));
            if(user == null) {
                ret.put("state",1);
                return ret;
            }
            Problem problem = problemService.findProblemByPid(pid);
            if(problem == null || (!problem.getUid().equals(user.getUid()) && user.getRole()<6)) {
                ret.put("state",1);
                return ret;
            }
            File dirs = new File("/var/oj/problem/"+pid+"/");
            if(!dirs.exists()) dirs.mkdirs();
            for(MultipartFile file : files){// /var/oj/problem
                String path = "/var/oj/problem/"+pid+"/"+file.getOriginalFilename();
                File newFile = new File(path);
                try {
                    file.transferTo(newFile);
                } catch (IOException e) {
                    System.out.println(e);
                }

                if ("spj.cpp".equals(file.getOriginalFilename())) {
                    String sourcePath = "/var/oj/problem/"+pid+"/spj.cpp";
                    String targetPath = "/var/oj/problem/"+pid+"/spj";
                    LinuxOptUtil.compileCPPToPath(sourcePath,targetPath);
                }
                if ("std.cpp".equals(file.getOriginalFilename())) {
                    String sourcePath = "/var/oj/problem/"+pid+"/std.cpp";
                    String targetPath = "/var/oj/problem/"+pid+"/std";
                    LinuxOptUtil.compileCPPToPath(sourcePath,targetPath);
                }
                if ("valid.cpp".equals(file.getOriginalFilename())) {
                    String sourcePath = "/var/oj/problem/"+pid+"/valid.cpp";
                    String targetPath = "/var/oj/problem/"+pid+"/valid";
                    LinuxOptUtil.compileCPPToPath(sourcePath,targetPath);
                }
            }
        }
        return ret;
    }

    @DeleteMapping("/api/problem/del/data")
    public Map<String,Object> delDataByPid(Integer pid,String filename,String token) throws UnsupportedEncodingException {
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        String uid = (String) stringRedisConnection.opsForHash().get(token,token);
        if(uid == null || uid.equals("")) ret.put("state",1);
        else{
            User user = userService.findUser(Integer.parseInt(uid));
            if(user == null) {
                ret.put("state",1);
                return ret;
            }
            Problem problem = problemService.findProblemByPid(pid);
            if(problem == null || (!problem.getUid().equals(user.getUid()) && user.getRole()<6)) {
                ret.put("state",1);
                return ret;
            }
            String path = "/var/oj/problem/"+pid+"/"+filename;
            File file = new File(path);
            if(!file.exists()) ret.put("state",1);
            else {
                file.delete();
            }
        }
        return ret;
    }

    @PostMapping("/api/problem/unzip/data")
    public Map<String,Object> unzipDataByPid(Integer pid,String filename,String token) throws IOException {
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        String uid = (String) stringRedisConnection.opsForHash().get(token,token);
        if(uid == null || uid.equals("")) ret.put("state",1);
        else{
            User user = userService.findUser(Integer.parseInt(uid));
            if(user == null) {
                ret.put("state",1);
                return ret;
            }
            Problem problem = problemService.findProblemByPid(pid);
            if(problem == null || (!problem.getUid().equals(user.getUid()) && user.getRole()<6)) {
                ret.put("state",1);
                return ret;
            }
            List<String> cmd = new ArrayList<>();
            cmd.add("unzip");
            cmd.add("/var/oj/problem/"+pid+"/"+filename);
            cmd.add("-d");
            cmd.add("/var/oj/problem/"+pid+"/");
            new ProcessBuilder(cmd).start();
        }
        return ret;
    }

    @PostMapping("/api/problem/batch/add")
    public void batchAddProblem(@RequestParam("datafile") MultipartFile file,String token) throws IOException {
        String uid = (String) stringRedisConnection.opsForHash().get(token,token);
        if(uid == null || uid.equals("")) return;
        User user = userService.findUser(Integer.parseInt(uid));
        if (user == null || user.getRole() < 2) return;
        File dirs = new File("/var/oj/problem/tmp/");
        if(!dirs.exists()) dirs.mkdirs();
        String path = sid.nextShort();
        File newFile = new File("/var/oj/problem/tmp/"+path+".zip");
        file.transferTo(newFile);
        LinuxOptUtil.unzipTo(newFile.getAbsolutePath(),"/var/oj/problem/tmp/"+path+"/");
        user.setLevel(user.getLevel()+20);
        user.setLiveness(user.getLiveness()+20);
        userService.updateUserInfo(user);
        try {
            File files = new File("/var/oj/problem/tmp/" + path + "/");
            for (File file1 : Objects.requireNonNull(files.listFiles(File::isDirectory))) {
                JSONObject jsonObject = JSONUtil.loadJSON(JSONUtil.getJSONString(file1.getAbsolutePath() + "/doj.json"));
                Problem problem = new Problem();
                problem.setTitle(Optional.ofNullable(jsonObject.getString("title")).orElse(""));
                problem.setDescription(Optional.ofNullable(jsonObject.getString("desc")).orElse(""));
                problem.setInput(Optional.ofNullable(jsonObject.getString("input")).orElse(""));
                problem.setOutput(Optional.ofNullable(jsonObject.getString("output")).orElse(""));
                problem.setType(Optional.ofNullable(jsonObject.getInteger("type")).orElse(0));
                problem.setSampleinput(Optional.ofNullable(jsonObject.getString("sinput")).orElse(""));
                problem.setSampleoutput(Optional.ofNullable(jsonObject.getString("soutput")).orElse(""));
                problem.setSpecial(Optional.ofNullable(jsonObject.getInteger("special")).orElse(0));
                problem.setTimelimit(Optional.ofNullable(jsonObject.getInteger("timeLimit")).orElse(1000));
                problem.setMemorylimit(Optional.ofNullable(jsonObject.getInteger("memoryLimit")).orElse(32));
                problem.setHint(Optional.ofNullable(jsonObject.getString("hint")).orElse(""));
                problem.setStatus(1);
                problem.setUid(user.getUid());
                problemService.insertProblem(problem);
                Integer pid = problemService.findProblemByTitle(problem.getTitle()).getPid();
                probleminfoService.insertProblemInfoByPid(pid);
                File tmp = new File("/var/oj/problem/" + pid + "/");
                if (!tmp.exists()) tmp.mkdirs();
                for (File file2: Objects.requireNonNull(file1.listFiles(File::isFile))) {
                    File dest = new File("/var/oj/problem/"+pid+"/"+file2.getName());
                    file2.renameTo(dest);
                }
                File file2 = new File("/var/oj/problem/" + pid + "/doj.json");
                if (file2.exists()) file2.delete();
                if (problem.getSpecial() == 1) {
                    String sourcePath = "/var/oj/problem/" + pid + "/spj.cpp";
                    String targetPath = "/var/oj/problem/" + pid + "/spj";
                    LinuxOptUtil.compileCPPToPath(sourcePath, targetPath);
                }

            }
            newFile.delete();
            LinuxOptUtil.deleteAllFromPath("/var/oj/problem/tmp/" + path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @DeleteMapping("/api/problem/delete")
    public Map<String,Object> delProblemByPid(Integer pid,HttpSession session) throws UnsupportedEncodingException {
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null||user.getLevel() < 7) ret.put("state",1);
        else if(!problemService.delProblemByPid(pid)) ret.put("state",1);
        else{
            delFile(new File("/var/oj/problem/"+pid+"/"));
            user.setLiveness(max(500,user.getLiveness()-20));
            user.setLevel(max(0,user.getLevel()-20));
            userService.updateUserInfo(user);
        }
        return ret;
    }

    private  boolean delFile(File file) throws UnsupportedEncodingException {
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
            return file.delete();
        }
    }
}
