package pers.dreamer.controller;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.*;
import pers.dreamer.dto.CodeDto;
import pers.dreamer.dto.ResultDto;
import pers.dreamer.dto.StatusDto;
import pers.dreamer.service.*;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.tools.MyUtil;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DateFormat;
import java.util.*;

import static java.lang.Math.max;

/**
 * 提交界面
 */
@RestController
@CrossOrigin
@EnableAsync
@EnableRabbit
//@PropertySource("classpath:codestyle.properties")
public class SubmitController {

    @Autowired
    ExlangService exlangService;

    @Autowired
    ContestService contestService;

    @Autowired
    ProblemService problemService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    UserService userService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    MessageService messageService;

    @Autowired
    ProbleminfoService probleminfoService;

    @Autowired
    AchievementService achievementService;

    @RabbitListener(queues = {"oj_jresult"})
    public void receiveJResult(ResultDto resultDto, org.springframework.amqp.core.Message message,Channel channel){//接收结果
        //System.out.println("接收到判题请求更新结果:"+resultDto);
        try {
            submitlistService.updateSubmitByResult(resultDto);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/api/submit")
    public List<String> getSubmitLimitByLang(Integer pid) {
        return exlangService.findSubmitLimitByLangAndPid(pid);
    }

    @PostMapping("/api/submit")
    public Map<String, Integer> submitCodeByNormal(@RequestBody CodeDto codeDto, Integer pid, HttpSession session) {
        Map<String, Integer> ret = new HashMap<>();
        ret.put("state", 0);
        User user = (User) session.getAttribute("user");
        if (user == null || codeDto == null) ret.put("state", 1);
        else {
            if(exlangService.findLangInExlangsByPidAndLang(pid,codeDto.getLang())){
                ret.put("state",1);
                return ret;
            }
            if (!problemService.isProblemCanBeViewed(pid,user)) {
                ret.put("state",1);
                return ret;
            }
            if (!submitlistService.isExistSubmitByCid(user.getUid(),-1)){
                userService.addLivenessAndLevelByUid(user.getUid(),10);
            }
            codeDto.setPid(pid);
            Integer sid = submitlistService.insertCodeAndSubmit(codeDto, user.getUid(),null);//user ==null ? -1 : user.getUid());
            execCode(sid);
        }
        return ret;
    }

    @GetMapping("/api/submit/info/{sid}")
    public Map<String, Object> getSubmitInfoBySid(@PathVariable("sid") Integer sid) throws IOException {
        Map<String,Object> ret = new HashMap<>();
        Submitlist submitlist = submitlistService.findSubmitlistBySid(sid);
        if (submitlist.getCid() != -1 && submitlist.getResult() != 9) {
            ret.put("state", 2);
        } else if (submitlist.getResult() == 9){
            ret.put("state",1);
            ret.put("msg","<p>"+submitlist.getMsg().replaceAll("\n","<br />")+"</p>");
        }
        return ret;

    }

    //CorrelationData中包含一个id(我们可以自定义，将其与发送的消息相关联)，当开启了confirm机制后，会收到包含该参数的回调，用于消息可靠性投递，可选的
    private CorrelationData corrData=new CorrelationData(String.valueOf(System.currentTimeMillis()));

    @Async
    public void execCode(Integer sid) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("oj.direct.judge", "oj.judge", sid, corrData);
    }

    @PostMapping("/api/submit/getpages")
    public Map<String,Object> getPagesByPidAndUsernameAndResAndLang(@RequestBody StatusDto statusDto) {
        Map<String,Object> ret = new HashMap<>();
        User user = userService.findUserByName(statusDto.getUsername());
        Long nums = submitlistService.findSubmitlistSizeByPageAndUidAndPidAndResAndLang(user != null && user.getUid() != null ? user.getUid() : -1,
                (statusDto.getPid() == null || statusDto.getPid() <= 0) ? -1 : statusDto.getPid(),
                (statusDto.getRes() == null || statusDto.getRes() < 0) ? -1 : statusDto.getRes(),
                (statusDto.getLang() == null || statusDto.getLang().length() == 0) ? null : statusDto.getLang());
        ret.put("pages", (nums+19)/20);
        ret.put("num",nums);
        return ret;
    }

    @PostMapping("/api/submit/list")
    public List<Map<String,Object>> getStatusByPidAndUsernameAndResAndLang(@RequestBody StatusDto statusDto, Integer page,HttpSession session)  {
        List<Map<String,Object>> ret = new ArrayList<>();
        if (page == null || page <= 0) page = 1;
        User user = userService.findUserByName(statusDto.getUsername());
        User nowUser = (User) session.getAttribute("user");
        List<Submitlist> lists = submitlistService.findSubmitlistByPageAndUidAndPidAndResAndLang(page - 1, 20,
                user != null && user.getUid() != null ? user.getUid() : -1,
                statusDto.getPid() == null || statusDto.getPid().compareTo(0) <= 0 ? -1 : statusDto.getPid(),
                statusDto.getRes() == null || statusDto.getRes().compareTo(0) < 0 ? -1 : statusDto.getRes(),
                statusDto.getLang() == null || statusDto.getLang().length() == 0 ? null : statusDto.getLang());

        boolean isNowUserNull = nowUser == null;
        for (Submitlist submitlist : lists) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("sid", submitlist.getSid());
            tmp.put("username", UserUtil.getStyleNameByUid(submitlist.getUid(),userService,achievementService));
            tmp.put("pid", submitlist.getPid());
            tmp.put("result", submitlist.getResult());
            tmp.put("lang", submitlist.getLang());
            tmp.put("time_used", submitlist.getTimeUsed());
            tmp.put("memory_used", submitlist.getMemoryUsed());
            tmp.put("size", submitlist.getSize());
            tmp.put("viewable",submitlistService.isSubmitCanBeViewedByUid(isNowUserNull?null:nowUser.getUid(),submitlist.getPid()));
            tmp.put("date", dateFormat.format(submitlist.getDate()));
            tmp.put("score", submitlist.getScore());
            tmp.put("pos",submitlist.getPos());
            tmp.put("state",isNowUserNull?0:(submitlist.getUid().equals(nowUser.getUid()) || nowUser.getRole()>=5));
            ret.add(tmp);
        }
        return ret;
    }

    @PutMapping("/api/submit/update")
    public Map<String, Object> updateSubmitlistBySid(@RequestBody CodeDto codeDto, Integer sid) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("state", 0);
        Submitlist submitlist = new Submitlist();
        submitlist.setSid(sid);
        submitlist.setResult(codeDto.getRes());
        if (codeDto.getScore() == null || codeDto.getScore().compareTo(0.0) < 0) codeDto.setScore(0.0);
        submitlist.setScore(codeDto.getScore());
        if (!submitlistService.updateSubmitlistBySid(submitlist)) ret.put("state", 1);
        return ret;
    }

    @DeleteMapping("/api/submit/delete")
    public Map<String, Object> deleteSubmitlistBySid(Integer sid,HttpSession session) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("state", 0);
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 7 || !submitlistService.deleteSubmitlistBySid(sid)) ret.put("state", 1);
        return ret;
    }

    @GetMapping("/api/submit/getcode")
    public Map<String, Object> getCodeBySidLimitAc(Integer sid, HttpSession session) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("state", 0);
        ret.put("code", null);
        User user = (User) session.getAttribute("user");
        if (user == null) ret.put("state", 1);
        else {
            Submitlist submitlist = submitlistService.findSubmitlistBySid(sid);
            if (submitlist.getCid() != -1 && !submitlist.getUid().equals(user.getUid())) {
                if (!contestService.isContestEnds(submitlist.getCid())) {
                    if (user.getRole() < 6 && !contestService.isContestAdminByUid(submitlist.getCid(),user.getUid())) {
                        ret.put("state",1);
                        return ret;
                    }
                }
            }
            if (!user.getUid().equals(submitlist.getUid()) && submitlistService.isSubmitCanBeViewedByUid(user.getUid(),submitlist.getPid()) && user.getRole()<5) {
                ret.put("state", 1);
            } else {
                User user1 = userService.findUserStyleNameByUid(submitlist.getUid());

                ret.put("collect",submitlistService.isCollectedByUid(submitlist.getSid(),user.getUid()));
                ret.put("result",submitlist.getResult());
                ret.put("username", user1.getUsername());
                ret.put("score",submitlist.getScore());
                ret.put("date",dateFormat.format(submitlist.getDate()));
                ret.put("pos",submitlist.getPos());
                ret.put("pid",submitlist.getPid());
                ret.put("code", MyUtil.codeDecode(submitlistService.findCodeByCodeid(submitlist.getSid()).getText()));

                ret.put("rating", user1.getRating());
                ret.put("time_used",submitlist.getTimeUsed());
                ret.put("memory_used",submitlist.getMemoryUsed());
                ret.put("lang",submitlist.getLang());

                ret.put("ptitle",problemService.findProblemTitleByPid(submitlist.getPid()));

                ret.put("size",submitlist.getSize());
            }
        }
        return ret;
    }

    @GetMapping("/api/submit/getcollect")
    public List<Map<String,Object>> getCollect(Integer uid) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = userService.findUserByUid(uid);
        if (user == null) {
            return ret;
        }
        List<Submitcollection> submitcollections = submitlistService.findSubmitCollectionByUid(uid);
        for (Submitcollection submitcollection:submitcollections) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("id",submitcollection.getId());
            tmp.put("sid",submitcollection.getSid());
            tmp.put("date",submitcollection.getDate());
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/submit/add/collection")
    public Map<String,Object> addCollection(Integer sid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state'",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else{
            Submitcollection submitcollection = new Submitcollection();
            submitcollection.setDate(new Date());
            submitcollection.setUid(user.getUid());
            submitcollection.setSid(sid);
            if(!submitlistService.insertSubmitCollection(submitcollection)) {
                delCollection(sid,session);
            }
        }
        return ret;
    }

    @DeleteMapping("/api/submit/del/collection")
    public Map<String,Object> delCollection(Integer sid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state'",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else if(!submitlistService.deleteSubmitCollectionByUidAndSid(user.getUid(),sid)) ret.put("state",1);
        return ret;
    }

    @PostMapping("/api/submit/rejudge/sid/{sid}")
    public Map<String,Object> reJudgeSid(@PathVariable("sid")Integer sid,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Submitlist submitlist = submitlistService.findSubmitlistBySid(sid);
        if (submitlist == null || user == null || user.getRole()<6) {
            ret.put("state",1);
        } else {
            submitlist.setResult(7);
            submitlist.setTimeUsed(null);
            submitlist.setMemoryUsed(null);
            submitlist.setScore(.0);
            submitlistService.updateSubmitlistBySid(submitlist);

            Probleminfo probleminfo = probleminfoService.findProblemInfoByPid(submitlist.getPid());
            switch (submitlist.getResult()){
                case 0:probleminfo.setAcCnt(max(probleminfo.getAcCnt()-1,0));break;
                case 1:probleminfo.setPeCnt(max(probleminfo.getPeCnt()-1,0));break;
                case 2:probleminfo.setTleCnt(max(probleminfo.getTleCnt()-1,0));break;
                case 3:probleminfo.setMleCnt(max(probleminfo.getMleCnt()-1,0));break;
                case 4:probleminfo.setWaCnt(max(probleminfo.getWaCnt()-1,0));break;
                case 5:probleminfo.setReCnt(max(probleminfo.getReCnt()-1,0));break;
                case 6:probleminfo.setOleCnt(max(probleminfo.getOleCnt()-1,0));break;
                case 8:probleminfo.setSeCnt(max(probleminfo.getSeCnt()-1,0));break;
                case 9:probleminfo.setCeCnt(max(probleminfo.getCeCnt()-1,0));break;
            }
            probleminfoService.updateProblemInfo(probleminfo);
            reJudgeCodeDto(submitlist.getSid());
            ret.put("state",0);
        }
        return ret;
    }

    @PostMapping("/api/submit/rejudge/pid/{pid}")
    public Map<String,Object> reJudgePid(@PathVariable("pid")Integer pid,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Problem problem = problemService.findProblemByPid(pid,user);
        if (problem == null || user == null || user.getRole()<6) {
            ret.put("state",1);
        } else {
            Probleminfo probleminfo = probleminfoService.findProblemInfoByPid(pid);
            probleminfo.setAcCnt(0);
            probleminfo.setPeCnt(0);
            probleminfo.setTleCnt(0);
            probleminfo.setMleCnt(0);
            probleminfo.setWaCnt(0);
            probleminfo.setReCnt(0);
            probleminfo.setCeCnt(0);
            probleminfo.setOleCnt(0);
            probleminfo.setSeCnt(0);
            probleminfoService.updateProblemInfo(probleminfo);

            List<Submitlist> submitlists = submitlistService.findSubmitlistsByPid(pid);
            for (Submitlist submitlist:submitlists) {
                submitlist.setResult(7);
                submitlist.setTimeUsed(null);
                submitlist.setMemoryUsed(null);
                submitlist.setScore(.0);
                submitlistService.updateSubmitlistBySid(submitlist);

                reJudgeCodeDto(submitlist.getSid());
            }
            ret.put("state",0);
        }
        return ret;
    }

    @PostMapping("/api/submit/rejudge/cid/{cid}")
    public Map<String,Object> reJudgeCid(@PathVariable("cid")Integer cid,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Contest contest = contestService.findContestInfoByCid(cid);
        if (contest == null || user == null || user.getRole()<6) {
            ret.put("state",1);
        } else {
            List<Submitlist> submitlists = submitlistService.findSubmitlistByCid(cid);
            for (Submitlist submitlist:submitlists) {
                submitlist.setResult(7);
                submitlist.setTimeUsed(null);
                submitlist.setMemoryUsed(null);
                submitlist.setScore(.0);
                submitlistService.updateSubmitlistBySid(submitlist);
                execCode(submitlist.getSid());
            }
            ret.put("state",0);
        }
        return ret;
    }

    private void reJudgeCodeDto(Integer sid) {
        execCode(sid);
    }
}
