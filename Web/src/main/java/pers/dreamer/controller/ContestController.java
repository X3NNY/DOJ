package pers.dreamer.controller;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pers.dreamer.bean.*;
import pers.dreamer.dto.*;
import pers.dreamer.exception.ContestNotFoundException;
import pers.dreamer.service.*;
import pers.dreamer.util.Pair;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.tools.MyUtil;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.*;

import static java.lang.Math.max;

@RestController
@CrossOrigin
public class ContestController {

    @Autowired
    ContestService contestService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    ProbleminfoService probleminfoService;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    UserService userService;

    @Autowired
    ExlangService exlangService;

    @Autowired
    ProblemService problemService;

    @Autowired
    MessageService messageService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AchievementService achievementService;

    //CorrelationData中包含一个id(我们可以自定义，将其与发送的消息相关联)，当开启了confirm机制后，会收到包含该参数的回调，用于消息可靠性投递，可选的
    private CorrelationData corrData=new CorrelationData(String.valueOf(System.currentTimeMillis()));

    @Async
    public void execCode(Integer sid) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend("oj.direct.judge", "oj.judge", sid, corrData);
    }

    @GetMapping("/api/contest/module/passwdcheck")
    public ModelAndView loadContestPasswdCheck() {
        return new ModelAndView("contestpasswdcheck");
    }

    @GetMapping("/api/contest/check")
    public Map<String,Integer> checkContest(Integer cid, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        Contest contest = contestService.findContestInfoByCid(cid);
        User user = (User) session.getAttribute("user");
        if (contest == null || user == null) {
            ret.put("state",2);
            return ret;
        }
        ret.put("state",0);
        if ((contest.getLevel() == 1 || contest.getLevel() == 3) && user.getRole() < 6) {
            Boolean flag = (Boolean) session.getAttribute("#C"+cid);
            if (flag == null || !flag) {
                ret.put("state",1);
            }
        }
        return ret;
    }

    @PostMapping("/api/contest/checkpasswd")
    public Map<String,Integer> checkPasswd(@RequestBody Contest contest,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        Contest c = contestService.findContestInfoByCid(contest.getCid());
        User user = (User) session.getAttribute("user");
        ret.put("state",1);
        if (c == null || user == null) {
            return ret;
        }
        if ((c.getLevel() == 1 || c.getLevel()==3) && c.getPassword().equals(contest.getPassword())) {
            ret.put("state",0);
            session.setAttribute("#C"+c.getCid(), Boolean.TRUE);
        }
        return ret;
    }

    @GetMapping("/api/contest")
    public Map<String,Object> getContests(Integer page, String name, HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        if(page != null) page = page.compareTo(0) <= 0?0:page-1;
        List<Contest> contests =  contestService.findContestByPageAndNameAndUid(page,10,name,-1);
        Set<Map<String,Object>> lists = new TreeSet<>(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int ret = ((String)o2.get("starttime")).compareTo ((String) o1.get("starttime"));
                return ret == 0 ? ((String)o2.get("endstime")).compareTo ((String) o1.get("endstime")) : ret;
            }

            @Override
            public boolean equals(Object obj) {
                return Objects.equals(this,obj);
            }
        });
        User user = (User) session.getAttribute("user");
        for(Contest contest : contests){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("cid",contest.getCid());
            List<User> users = contestService.findContestForAuthorsByCid(contest.getCid());
            List<String> builders = new ArrayList<>();
            for( User u : users) builders.add(u.getUsername()+ "|" +u.getRating());
            tmp.put("builders",builders);
            tmp.put("title",contest.getTitle());
            tmp.put("starttime",dateFormat.format(contest.getStarttime()));
            tmp.put("endstime",dateFormat.format(contest.getEndstime()));
            tmp.put("level",contest.getLevel());
            tmp.put("up_vote",contest.getUpVote());
            tmp.put("down_vote",contest.getDownVote());
            tmp.put("my_vote",user != null ? contestService.findMyVoteByUidAndCid(user.getUid(),contest.getCid()) : 0);
            tmp.put("num",contestService.findRegisterTotalOnContestByCid(contest.getCid()));
            tmp.put("state",user == null ? 0 : contestService.findStateOnContestByUidAndCid(user.getUid(),contest.getCid()));
            lists.add(tmp);
        }
        ret.put("pages",(contestService.findContestSizeByPageAndName(name)+9)/10);
        ret.put("contests",lists);
        return ret;
    }

    @GetMapping("/api/contest/diy")
    public Map<String,Object> getDiyContests(Integer page,String name,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        if(page != null) page = page.compareTo(0) <= 0?0:page-1;
        List<Contest> contests =  contestService.findContestByPageAndNameAndUid(page,20,name,null);
        List<Map<String,Object>> lists = new ArrayList<>();

        User user = (User) session.getAttribute("user");
        for(Contest contest : contests){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("cid",contest.getCid());
            User u = userService.findUserStyleNameByUid(contest.getUid());
            tmp.put("builders",u.getUsername()+ "|" +u.getRating());
            tmp.put("title",contest.getTitle());
            tmp.put("starttime",dateFormat.format(contest.getStarttime()));
            tmp.put("endstime",dateFormat.format(contest.getEndstime()));
            tmp.put("level",contest.getLevel());
            tmp.put("up_vote",contest.getUpVote());
            tmp.put("down_vote",contest.getDownVote());
            tmp.put("my_vote",user != null ? contestService.findMyVoteByUidAndCid(user.getUid(),contest.getCid()) : 0);
            tmp.put("num",contestService.findRegisterTotalOnContestByCid(contest.getCid()));
            tmp.put("state",user == null ? 0 : contestService.findStateOnContestByUidAndCid(user.getUid(),contest.getCid()));
            lists.add(tmp);
        }
        ret.put("pages",(contestService.findContestSizeByPageAndName(name)+19)/20);
        ret.put("contests",lists);
        return ret;
    }

    @GetMapping("/api/contest/all")
    public Map<String,Object> getAllContest(Integer page,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            ret.put("state",1);
            return ret;
        }
        if (page == null || page <= 0) page = 1;
        List<Contest> contests =  contestService.findAllContestByPage(page-1,10);
        Set<Map<String,Object>> lists = new TreeSet<>(new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int ret = ((String)o2.get("starttime")).compareTo ((String) o1.get("starttime"));
                return ret == 0 ? ((String)o2.get("endstime")).compareTo ((String) o1.get("endstime")) : ret;
            }

            @Override
            public boolean equals(Object obj) {
                return Objects.equals(this,obj);
            }
        });

        for(Contest contest : contests){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("cid",contest.getCid());
            List<User> users = contestService.findContestForAuthorsByCid(contest.getCid());
            List<String> builders = new ArrayList<>();
            for( User u : users) builders.add(u.getUsername()+ "|" +u.getRating());
            tmp.put("builders",builders);
            tmp.put("title",contest.getTitle());
            tmp.put("starttime",dateFormat.format(contest.getStarttime()));
            tmp.put("endstime",dateFormat.format(contest.getEndstime()));
            tmp.put("level",contest.getLevel());
            tmp.put("up_vote",contest.getUpVote());
            tmp.put("status",contest.getStatus());
            tmp.put("down_vote",contest.getDownVote());
            tmp.put("num",contestService.findRegisterTotalOnContestByCid(contest.getCid()));
            lists.add(tmp);
        }
        ret.put("pages",(contests.size()+9)/10);
        ret.put("contests",lists);
        return ret;
    }

    @GetMapping("/api/contest/info/{cid}")
    public Map<String,Object> getContestInfoByCid(@PathVariable("cid") Integer cid,HttpSession session){
        Map<String, Object> ret = new HashMap<>();
        Contest contest = contestService.findContestInfoByCid(cid);
        User user = (User) session.getAttribute("user");
        if(contest == null || user == null) {
            ret.put("state",3);
            return ret;
        }
        if (!checkUserPermit(contest,user,session)) {
            ret.put("state",3);
            return ret;
        }
        List<Map<String,Object>> lists = new ArrayList<>();
        ret.put("cid",cid);
        ret.put("title",contest.getTitle());
        ret.put("status",contest.getStatus());
        ret.put("starttime",dateFormat.format(contest.getStarttime()));
        ret.put("endstime",dateFormat.format(contest.getEndstime()));
        ret.put("desc",contest.getDescription());
        ret.put("type",contest.getType());
        ret.put("problemset",lists);
        ret.put("is_author",user.getRole()>=6||contest.getUid().equals(user.getUid()));
        Contestregister contestregister = contestService.findContestReigsterByUidAndCid(user.getUid(),cid);
        if (user.getRole()<6 && contestregister == null && contest.getEndstime().getTime()>System.currentTimeMillis()) { //未注册
            ret.put("state",2);
            return ret;
        }
        if (user.getRole()<6 && contest.getStarttime().getTime() > System.currentTimeMillis() && !contest.getUid().equals(user.getUid())) { // 未开始非管理员不能看题
            ret.put("state",1);
            return ret;
        }
        ret.put("state",0);
        if (contest.getUid().equals(user.getUid()) || (contestregister != null && contestregister.getRole() == 1) || user.getRole()>=6) {
            ret.put("is_admin",true);
        } else {
            ret.put("is_admin",false);
        }
        List<Problem> problems = contestService.findProblemsTitleByCid(cid);
        for(Problem problem : problems){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("pid",problem.getPid());
            tmp.put("title",problem.getTitle());
            tmp.put("state",contestregister==null?0:submitlistService.findContestStatusOnPorblemByCidAndUidAndPid(contest.getCid(),user.getUid(),problem.getPid()));
            tmp.put("ac_cnt",contestService.findProblemAcCntByPid(contest.getCid(),problem.getPid()));
            tmp.put("all_cnt",contestService.findProblemAllCntByPid(contest.getCid(),problem.getPid()));
            lists.add(tmp);
        }
        ret.put("problemset",lists);
        return ret;
    }

    @GetMapping("/api/contest/sinfo/{cid}")
    public Map<String,Object> getContestSInfoByCid(@PathVariable("cid") Integer cid,HttpSession session){
        Map<String, Object> ret = new HashMap<>();
        Contest contest = contestService.findContestInfoByCid(cid);
        User user = (User) session.getAttribute("user");
        if(contest == null || user == null) {
            ret.put("state",3);
            return ret;
        }
        if (user.getRole()<=6 && !contest.getUid().equals(user.getUid())) { // 未开始非管理员不能看题
            ret.put("state",1);
            return ret;
        }
        List<Map<String,Object>> lists = new ArrayList<>();
        ret.put("cid",cid);
        ret.put("title",contest.getTitle());
        ret.put("status",contest.getStatus());
        ret.put("starttime",dateFormat.format(contest.getStarttime()));
        ret.put("endstime",dateFormat.format(contest.getEndstime()));
        ret.put("desc",contest.getDescription());
        ret.put("type",contest.getType());
        ret.put("level",contest.getLevel());
        ret.put("password",contest.getPassword());
        ret.put("is_author",user.getRole()>=6||contest.getUid().equals(user.getUid()));
        ret.put("state",0);
        List<Problem> problems = contestService.findProblemsByCid(cid);
        for(Problem problem : problems){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("pid",problem.getPid());
            tmp.put("title",problem.getTitle());
            lists.add(tmp);
        }
        ret.put("problemset",lists);
        return ret;
    }

    private boolean checkUserPermit(Contest contest, User user,HttpSession session) { //检查用户能否进入本场比赛
        if (contest == null) {
            return false;
        }
        if (user != null && user.getRole() >= 6) {
            return true;
        }
        if (contest.getLevel() == 1 || contest.getLevel() == 3) {
            Boolean flag = (Boolean) session.getAttribute("#C"+contest.getCid());
            return flag != null && flag;
        }
        return true;
    }

    @PostMapping("/api/contest/submit/{cid}/{pid}")
    public Map<String, Integer> submitCodeByContest(@RequestBody CodeDto codeDto, @PathVariable("cid") Integer cid, @PathVariable("pid") Integer pid, HttpSession session) {
        Map<String, Integer> ret = new HashMap<>();
        ret.put("state", 0);
        User user = (User) session.getAttribute("user");
        if (user == null
                || !contestService.findUserRegisterOnTheContest(user.getUid(),cid)
                || exlangService.findLangInExlangsByPidAndLang(pid,codeDto.getLang())) {
            ret.put("state", 1);
        } else {
            Contest contest = contestService.findContestInfoByCid(cid);
            if (!checkUserPermit(contest,user,session)) {
                return ret;
            }
            long time = System.currentTimeMillis();
            if(time < contest.getStarttime().getTime() || time > contest.getEndstime().getTime()) ret.put("state",1);
            else {
                if (!problemService.isExistProblem(pid)){
                    ret.put("state",2);
                    return ret;
                }
                if (contestService.isValidUser(cid,user.getUid())) {
                    contestService.updateRegisterByUidAndCid(user.getUid(), cid);
                    if (contest.getUid() == -1) {
                        user.setLiveness(user.getLevel() + 20);
                        user.setLiveness(user.getLiveness() + 20);
                        user.setCoins(user.getCoins() + 5);
                        pers.dreamer.bean.Message message = new pers.dreamer.bean.Message();
                        message.setStatus(0);
                        message.setDate(new Date());
                        message.setTitle("参加竞赛:+5 DC 任务达成!");
                        message.setContent("当前您的DC数量为:" + user.getCoins() + " DC");
                        message.setSenduid(-1);
                        message.setTargetuid(user.getUid());
                        messageService.insertMessage(message);
                        userService.updateUserInfo(user);
                    }
                }

                codeDto.setPid(pid);

                switch (contest.getType()) {
                    case 0:
                        submitICPC(user.getUid(),cid,codeDto);break;
                    case 1:
                        submitOI(user.getUid(),cid,codeDto);break;
                    case 2:
                        submitIOI(user.getUid(),cid,codeDto);break;
                    case 3:
                        submitShortCode(user.getUid(),cid,codeDto);break;
                    case 4:
                        submitMinTime(user.getUid(),cid,codeDto);break;
                }
            }
        }
        return ret;
    }

    @GetMapping("/api/contest/problem/{cid}/{num}")
    public Problem getProblemByCidAndNum(@PathVariable("cid") Integer cid,@PathVariable("num") Integer num,HttpSession session){
        Contest valid = contestService.findContestInfoByCid(cid);
        User user = (User) session.getAttribute("user");
        if (!checkUserPermit(valid,user,session)) {
            return null;
        }
        if(valid.getStarttime().getTime() > System.currentTimeMillis()) return null;
        Problem problem = contestService.findProblemsByCidAndNum(cid, num);
        if(problem != null) problem.setState(submitlistService.findStatusOnProblemByCidAndUidAndPid(cid,user!=null?user.getUid():null,problem.getPid()));
        return problem;
    }

    @GetMapping("/api/contest/{cid}/getpages")
    public Long getStatusPagesByCidAndPage(@PathVariable("cid")Integer cid,String name,Integer num,Integer res,String lang) {
        Integer uid = null;
        Integer pid = null;
        if (!"".equals(name))  {
            uid = userService.findUidByUsername(name);
        }
        if (num >= 0) {
            pid = contestService.findPidByCidAndNum(cid,num);
        }
        return (contestService.findStatusSizeOnContestByCidAndPageAndUidAndPidAndResAndLang(cid,uid,pid,res,lang)+9)/10;
    }

    @PostMapping("/api/contest/status/{cid}/{page}")
    public Map<String,Object> getStatusOnContestByCidAndPage(@RequestBody StatusDto statusDto, @PathVariable("cid") Integer cid, @PathVariable("page") Integer page,HttpSession session) throws UnsupportedEncodingException {
        Contest valid = contestService.findContestInfoByCid(cid);
        Map<String, Object> ret= new HashMap<>();
        if(valid == null || valid.getStarttime().getTime() > System.currentTimeMillis()) {
            ret.put("state",3);
            return ret;
        }
        if(page == null || page.compareTo(0) < 0) page = 1;
        User u = (User) session.getAttribute("user");
        Integer uid = null;
        Integer pid=null;
        if (statusDto.getUsername() != null) {
            uid = userService.findUidByUsername(statusDto.getUsername());
        }
        if(statusDto.getNum()!= null && statusDto.getNum() >= 0) {
            pid = contestService.findPidByCidAndNum(cid,statusDto.getNum());
        }
        List<Submitlist> submitlists = contestService.findStatusOnContestByCidAndPageAndUidAndPidAndResAndLang(cid,page-1,10,uid, pid,statusDto.getRes(),statusDto.getLang());
        List<Map<String,Object>> lists = new ArrayList<>();
        boolean isCanViewed = u != null && (u.getRole()>=6 || valid.getUid().equals(u.getUid()));
        boolean showScore = valid.getType() == 1 || valid.getType() == 2;
        for (Submitlist submitlist:submitlists) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("sid",submitlist.getSid());
            tmp.put("username",UserUtil.getStyleNameByUid(submitlist.getUid(),userService,achievementService));
            tmp.put("pid",submitlist.getPid());
            tmp.put("result",submitlist.getResult());
            tmp.put("state",(isCanViewed || (u != null && submitlist.getUid().equals(u.getUid())))?1:0);
            tmp.put("lang",submitlist.getLang());
            if (isCanViewed || (u != null && submitlist.getUid().equals(u.getUid()))) {
                tmp.put("time_used", submitlist.getTimeUsed());
                tmp.put("memory_used", submitlist.getMemoryUsed());
            }
            tmp.put("size",submitlist.getSize());
            tmp.put("date",submitlist.getDate());
            if (showScore) {
                tmp.put("score", submitlist.getScore());
            }
            lists.add(tmp);
        }
        ret.put("status",lists);
        return ret;
    }

    @GetMapping("/api/contest/status/user/{cid}")
    public  Map<String,Object> getStatusOnContestForUserByCid(@PathVariable("cid") Integer cid,HttpSession session){
        Contest valid = contestService.findContestInfoByCid(cid);
        Map<String, Object> ret= new HashMap<>();
        if(valid == null || valid.getStarttime().getTime() > System.currentTimeMillis()) {
            ret.put("state",3);
            return ret;
        }
        User user = (User) session.getAttribute("user");
        ret.put("state",user != null ? 1 : 0);
        ret.put("username",UserUtil.getStyleNameByUser(user,achievementService));
        List<Map<String,Object>> lists = new ArrayList<>();
        List<Submitlist> submitlists = submitlistService.findSubmitlistsByCidAndUid(cid,user != null ? user.getUid() : null);
        for(Submitlist submitlist : submitlists){
            Map<String, Object> tmp = new HashMap<>();
            tmp.put("sid",submitlist.getSid());
            tmp.put("num",contestService.findNumOnContestByPid(submitlist.getPid()));
            tmp.put("result",submitlist.getResult());
            lists.add(tmp);
        }
        ret.put("status",lists);
        return ret;
    }

    @GetMapping("/api/contest/getcode")
    public Map<String,Object> getCodeBySid(Integer sid,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Submitlist submitlist = submitlistService.findSubmitlistBySid(sid);
        ret.put("state",1);
        if (submitlist.getCid() == -1) {
            return ret;
        }
        if (!submitlist.getUid().equals(user.getUid()) && user.getRole() < 6 && !contestService.isContestAdminByUid(submitlist.getCid(),user.getUid())) {
            return ret;
        }
        ret.put("state",0);
        ret.put("result",submitlist.getResult());
        ret.put("username", userService.findUsernameByUid(submitlist.getUid()));
        ret.put("score",submitlist.getScore());
        ret.put("date",dateFormat.format(submitlist.getDate()));
        ret.put("code", MyUtil.codeDecode(submitlistService.findCodeByCodeid(submitlist.getSid()).getText()));
        return ret;
    }

    @GetMapping("/api/contest/rank/{cid}")
    public Map<String,Object> getRankOnContestByCid(@PathVariable("cid") Integer cid){
        Map<String,Object> ret = new HashMap<>();
        Contest contest = contestService.findContestTimeAndTypeInfoByCid(cid);
        if(contest == null || contest.getStarttime().getTime() > System.currentTimeMillis()) {
            return ret;
        }
        switch (contest.getType()) {
            case 0:
                ret = rankICPC(contest);break;
            case 1:
                ret = rankOI(contest);break;
            case 2:
                ret = rankIOI(contest);break;
            case 3:
                ret = rankShortCode(contest);break;
            case 4:
                ret = rankMinTime(contest);break;
        }
        return ret;
    }

    @GetMapping("/api/contest/notice/{cid}")
    public List<Map<String,Object>> getNoticeOnContestByCidUsingGet(@PathVariable("cid") Integer cid){
        Contest valid = contestService.findContestInfoByCid(cid);
        List<Map<String, Object>> ret= new ArrayList<>();
        long nowTime = System.currentTimeMillis();
        if(valid == null || valid.getStarttime().getTime() > nowTime) {
            return ret;
        }
        List<Contestnotice> contestnotices = contestService.findNoticesOnContestByCid(cid);
        for(Contestnotice contestnotice : contestnotices){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("nid",contestnotice.getId());
            tmp.put("username",UserUtil.getStyleNameByUid(contestnotice.getUid(),userService,achievementService));
            tmp.put("num",contestService.findNumOnContestByPid(contestnotice.getPid()));
            tmp.put("qdate",dateFormat.format(contestnotice.getQdate()));
            tmp.put("adate",contestnotice.getAdate()==null?null:dateFormat.format(contestnotice.getAdate()));
            tmp.put("question",contestnotice.getQuestion());
            tmp.put("answer",contestnotice.getAnswer());
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/contest/notice/{cid}")
    public Map<String,Object> getNoticeOnContestByCidUsingPost(@RequestBody NoticeDto noticeDto, @PathVariable("cid") Integer cid,HttpSession session){
        Contest valid = contestService.findContestInfoByCid(cid);
        Map<String, Object> ret= new HashMap<>();
        long nowTime = System.currentTimeMillis();
        if(valid == null || valid.getStarttime().getTime() > nowTime || valid.getEndstime().getTime() < nowTime) {
            ret.put("state",3);
            return ret;
        }
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null || cid == null) ret.put("state",1);
        else{
            Contestregister contestregister = contestService.findContestReigsterByUidAndCid(user.getUid(),cid);
            if (contestregister == null) {
                ret.put("state",1);
                return ret;
            }
            if (noticeDto.getNum() ==null || noticeDto.getNum() < 0) {
                noticeDto.setNum(-1);
                contestService.insertNotice(cid,-1,user.getUid(),noticeDto.getQuestion());
            } else {
                contestService.insertNotice(cid,contestService.findPidByCidAndNum(cid,noticeDto.getNum()),user.getUid(),noticeDto.getQuestion());
            }
        }
        return ret;
    }

    @PostMapping("/api/contest/notice/reply/{nid}")
    public Map<String,Object> replyDiscuss(@PathVariable("nid")Integer nid,@RequestBody NoticeDto noticeDto,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        ret.put("state",1);
        if (user == null) {
            return ret;
        }
        Contestnotice contestnotice = contestService.findNoticeByNid(nid);
        if (contestnotice == null) {
            return ret;
        }
        Contest contest = contestService.findContestInfoByCid(contestnotice.getCid());
        if (contest == null) {
            return ret;
        }
        Contestregister contestregister = contestService.findContestReigsterByUidAndCid(user.getUid(),contest.getCid());

        if (user.getRole()<6 && (contestregister == null || contestregister.getRole() != 1) && !contest.getUid().equals(user.getUid())) {
            return ret;
        }

        contestnotice.setAdate(new Date());
        contestnotice.setAnswer(noticeDto.getAnswer());
        contestService.updateContestnoticeByNid(contestnotice);

        Message message = new Message();
        message.setTitle("回复通知");
        message.setTargetuid(contestnotice.getUid());
        message.setDate(contestnotice.getAdate());
        message.setSenduid(-2);
        message.setContent("您在比赛<a href='contest.jsp?cid="+contest.getCid()+"'>"+contest.getTitle()+"</a>中的提问管理员已回复，请点击查看。");
        messageService.insertMessage(message);

        ret.put("state",0);
        return ret;
    }

    @PostMapping("/api/contest/register/{cid}")
    public Map<String, Object> registerContestByCid(@PathVariable("cid") Integer cid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",0);
        else{
            Contest contest = contestService.findContestInfoByCid(cid);
            if (System.currentTimeMillis() >= contest.getStarttime().getTime()) {
                ret.put("state",-1);
                return ret;
            }
            Contestregister contestregister = contestService.findContestReigsterByUidAndCid(user.getUid(),cid);
            if(contestregister == null){
                contestregister = new Contestregister();
                contestregister.setStatus(0);
                contestregister.setDate(new Date());
                contestregister.setCid(cid);
                contestregister.setLastrating(user.getRating());
                contestregister.setRole(0);
                contestregister.setUid(user.getUid());
                contestService.insertContestRegister(contestregister);
                ret.put("state",1);
            }else {
                contestService.delContestRegister(contestregister);
                ret.put("state",2);
            }
        }
        return ret;
    }

    @GetMapping("/api/contest/register/info/{cid}")
    public Map<String,Object> getRegisterInfoByCid(@PathVariable("cid") Integer cid,Integer page,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (page ==null || page<0) page=1;
        List<Contestregister> contestregisters = contestService.findRestersOnContestByCidAndPage(cid,page-1);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Contestregister contestregister:contestregisters) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("username",UserUtil.getStyleNameByUid(contestregister.getUid(),userService,achievementService));
            tmp.put("date",contestregister.getDate());
            tmp.put("rating",contestregister.getLastrating());
            tmp.put("state",contestregister.getStatus());
            list.add(tmp);
        }
        if (user==null) {
            ret.put("my_state", 0);
        } else {
            ret.put("my_state",contestService.findContestReigsterByUidAndCid(user.getUid(),cid)==null?0:1);
        }
        ret.put("pages",(contestService.findRegisterTotalOnContestByCid(cid)+49)/50);
        ret.put("info",list);
        return ret;
    }

    @PostMapping("/api/contest/new")
    public Map<String,Integer> addNewContest(@RequestBody NewContestDto newContestDto,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            ret.put("state",1);
        } else {
            Contest contest = new Contest();
            contest.setTitle(newContestDto.getTitle());
            contest.setStarttime(newContestDto.getStartTime());
            contest.setEndstime(newContestDto.getEndsTime());
            contest.setDescription(newContestDto.getDesc());
            contest.setType(newContestDto.getType());
            contest.setLevel(newContestDto.getLevel());
            contest.setPassword(newContestDto.getPassword());
            contest.setStatus(0);
            contest.setUid(-1);
            contestService.insertContest(contest);
            contest = contestService.findContestByTitleAndUidAndLevel(contest.getTitle(),-1,0);
            if (contest == null) {
                ret.put("state",1);
            } else {
                Contestregister contestregister = new Contestregister();
                contestregister.setStatus(2);
                contestregister.setRole(1);
                contestregister.setUid(user.getUid());
                contestregister.setLastrating(user.getRating());
                contestregister.setCid(contest.getCid());
                contestregister.setDate(new Date());
                contestService.insertContestRegister(contestregister);

                int tot = 0;
                for (int pos = 0; pos < newContestDto.getProblems().length; pos++) {
                    Problem problem = problemService.findProblemByPid(newContestDto.getProblems()[pos],user);
                    if (problem == null) continue;
                    Contestproblems contestproblems = new Contestproblems();
                    contestproblems.setCid(contest.getCid());
                    contestproblems.setPid(newContestDto.getProblems()[pos]);
                    contestproblems.setNum(tot++);
                    contestService.insertContestProblem(contestproblems);

                    int rating = userService.findRatingByUid(problem.getUid());
                    contestregister = new Contestregister();
                    contestregister.setStatus(2);
                    contestregister.setRole(0);
                    contestregister.setUid(problem.getUid());
                    contestregister.setLastrating(rating);
                    contestregister.setCid(contest.getCid());
                    contestregister.setDate(new Date());
                    contestService.insertContestRegister(contestregister);
                }
                ret.put("state",0);
            }
        }
        return ret;
    }

    @PostMapping("/api/contest/diy/new")
    public Map<String,Integer> addNewDiyContest(@RequestBody NewContestDto newContestDto,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ret.put("state",1);
        } else {
            Contest contest = new Contest();
            contest.setTitle(newContestDto.getTitle());
            contest.setStarttime(newContestDto.getStartTime());
            contest.setEndstime(newContestDto.getEndsTime());
            contest.setDescription(newContestDto.getDesc());
            contest.setType(newContestDto.getType());
            contest.setLevel(newContestDto.getLevel()+2);
            contest.setPassword(newContestDto.getPassword());
            contest.setStatus(0);
            contest.setUid(user.getUid());
            contestService.insertContest(contest);
            contest = contestService.findContestByTitleAndUidAndLevel(contest.getTitle(),user.getUid(),newContestDto.getLevel()+2);
            if (contest == null) {
                ret.put("state",1);
            } else {
                Contestregister contestregister = new Contestregister();
                contestregister.setStatus(2);
                contestregister.setRole(1);
                contestregister.setUid(user.getUid());
                contestregister.setLastrating(user.getRating());
                contestregister.setCid(contest.getCid());
                contestregister.setDate(new Date());
                contestService.insertContestRegister(contestregister);

                int tot = 0;
                for (int pos = 0; pos < newContestDto.getProblems().length; pos++) {
                    Problem problem = problemService.findProblemByPid(newContestDto.getProblems()[pos],user);
                    if (problem == null) continue;
                    Contestproblems contestproblems = new Contestproblems();
                    contestproblems.setCid(contest.getCid());
                    contestproblems.setPid(newContestDto.getProblems()[pos]);
                    contestproblems.setNum(tot++);
                    contestService.insertContestProblem(contestproblems);
                }
                ret.put("state",0);
            }
        }
        return ret;
    }

    @PutMapping("/api/contest/edit/{cid}")
    public Map<String,Integer> editContest(@RequestBody NewContestDto newContestDto,@PathVariable("cid") Integer cid,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Contest contest = contestService.findContestInfoByCid(cid);
        if (user == null || contest == null || (user.getRole()<6 && !contest.getUid().equals(user.getUid()))) {
            ret.put("state",1);
        } else {
            contest.setPassword(newContestDto.getPassword());
            contest.setType(newContestDto.getType());
            contest.setLevel(contest.getUid() == -1?newContestDto.getLevel():(newContestDto.getLevel()+2));
            contest.setTitle(newContestDto.getTitle());
            contest.setStarttime(newContestDto.getStartTime());
            contest.setEndstime(newContestDto.getEndsTime());
            contest.setDescription(newContestDto.getDesc());
            contestService.updateContest(contest);

            contestService.deleteAllContestproblemByCid(cid);
            int tot = 0;
            for (int pos = 0; pos < newContestDto.getProblems().length; pos++) {
                Problem problem = problemService.findProblemByPid(newContestDto.getProblems()[pos],user);
                if (problem == null) continue;
                Contestproblems contestproblems = new Contestproblems();
                contestproblems.setCid(contest.getCid());
                contestproblems.setPid(newContestDto.getProblems()[pos]);
                contestproblems.setNum(tot++);
                contestService.insertContestProblem(contestproblems);
            }
            ret.put("state",0);
        }
        return ret;
    }

    @PostMapping("/api/contest/upvote/{cid}")
    public Map<String,Object> upVote(@PathVariable("cid") Integer cid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null || contestService.findMyVoteByUidAndCid(user.getUid(),cid) != 0) ret.put("state",1);
        else {
            contestService.insertMyVoteForContest(cid,user.getUid(),true);
            user = contestService.findAuthorByCid(cid);
            user.setLiveness(user.getLiveness()+5);
            user.setLevel(user.getLevel()+5);
            userService.updateUserInfo(user);
        }
        return ret;
    }

    @PostMapping("/api/contest/downvote/{cid}")
    public Map<String,Object> downVote(@PathVariable("cid") Integer cid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null || contestService.findMyVoteByUidAndCid(user.getUid(),cid) != 0) ret.put("state",1);
        else {
            contestService.insertMyVoteForContest(cid,user.getUid(),false);
            user = contestService.findAuthorByCid(cid);
            user.setLiveness(max(500,user.getLiveness()-5));
            user.setLevel(max(user.getLevel()-5,0));
            userService.updateUserInfo(user);
        }
        return ret;
    }

    @PostMapping("/api/contest/clone")
    public Map<String,Integer> cloneContest(Integer cid, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Contest contest = contestService.findContestInfoByCid(cid);
        long nowTime = System.currentTimeMillis();
        if (user == null || contest == null || contest.getEndstime().getTime() > nowTime) {
            ret.put("state",1);
            return ret;
        }

        ret.put("state",0);
        List<Contestproblems> contestproblems = contestService.findOrderProblemsByCid(cid);
        Contest cloneContest = new Contest();
        cloneContest.setLevel(2);
        cloneContest.setStarttime(new Date(nowTime+5*1000*60));
        cloneContest.setEndstime(new Date(nowTime+5*1000*60+ 1000L *60*MyUtil.getDateMinutesDifference(contest.getEndstime(),contest.getStarttime())));
        cloneContest.setStatus(0);
        cloneContest.setType(contest.getType());
        cloneContest.setTitle(contest.getTitle()+"[Clone]");
        cloneContest.setDescription(contest.getDescription());
        cloneContest.setUid(user.getUid());
        contestService.insertContest(cloneContest);

        cloneContest = contestService.findContestByTitleAndUidAndLevel(cloneContest.getTitle(),cloneContest.getUid(),2);
        for (Contestproblems contestproblems1:contestproblems) {
            contestproblems1.setId(null);
            contestproblems1.setCid(cloneContest.getCid());
            contestService.insertContestProblem(contestproblems1);
        }
        Contestregister contestregister = new Contestregister();
        contestregister.setStatus(0);
        contestregister.setRole(0);
        contestregister.setUid(user.getUid());
        contestregister.setLastrating(user.getRating());
        contestregister.setCid(cloneContest.getCid());
        contestregister.setDate(new Date());
        contestService.insertContestRegister(contestregister);
        ret.put("cid",cloneContest.getCid());
        return ret;
    }

    @PutMapping("/api/contest/calc/{cid}")
    public Map<String,Object> calcUserRating(@PathVariable("cid")Integer cid,String key, HttpSession session) throws ContestNotFoundException {
        Map<String,Object> ret = new HashMap<>();
        Contest contest = contestService.findContestInfoByCid(cid);
        User user = (User) session.getAttribute("user");
        if (contest == null || user == null || contest.getLevel()!=0 || user.getRole() < 6 || contest.getUid() != -1) {
            ret.put("state",1);
            return ret;
        }

        if (contest.getStatus() != 0 && !key.equals("cxsfnszdnp")) {
            ret.put("state",1);
            return ret;
        }

        List<RankDto> lists;

        switch (contest.getType()) {
            case 0:
                lists = (List<RankDto>) rankICPC(contest).get("set");break;
            case 1:
                lists = (List<RankDto>) rankOI(contest).get("set");break;
            case 2:
                lists = (List<RankDto>) rankIOI(contest).get("set");break;
            case 3:
                lists = (List<RankDto>) rankShortCode(contest).get("set");break;
            case 4:
                lists = (List<RankDto>) rankMinTime(contest).get("set");break;
            default:
                throw new IllegalStateException("Unexpected value: " + contest.getType());
        }

        lists.removeIf(rankDto -> rankDto.getState() != 0);
        lists.sort((o1,o2) -> {
            if (o1.getCnt() == o2.getCnt()) {
                return o1.getPenalty() < o2.getPenalty() ? -1 : 1;
            }
            return o1.getCnt() > o2.getCnt() ? -1 : 1;
        });
        int tot = 1;
        for (RankDto it : lists) {
            contestService.updateRegisterRankByUid(contest.getCid(),it.getUid(),tot++);
        }

        List<Map<Integer,Integer>> rating = getRatingChange(cid);
        for (Map<Integer, Integer> it : rating) {
            for (Map.Entry<Integer,Integer> i:it.entrySet()) {
                User u = userService.findUserByUid(i.getKey());
                int pre = u.getRating() > 0?u.getRating():Integer.MAX_VALUE;
                u.setRating(i.getValue()>=0?i.getValue():0);
                u.setMaxscore(Math.max(u.getRating(),u.getMaxscore()));
                if(u.getRating() > pre) {
                    u.setLiveness(u.getLiveness() + (u.getRating() - pre)/2);
                    u.setLevel(u.getLevel() + (u.getRating() - pre)/2);
                    Message message = new Message();
                    message.setTitle("比赛积分奖励");
                    int t = u.getCoins();
                    u.setCoins(t+(u.getRating()-pre)/5);
                    message.setContent("您在比赛"+contest.getTitle()+"中涨了"+(u.getRating()-pre)+"分，获得DC： +"+(u.getCoins()-t)+"，现在共有"+u.getCoins()+"DC，继续保持噢！");
                    message.setStatus(0);
                    message.setTargetuid(u.getUid());
                    message.setSenduid(-1);
                    message.setDate(new Date());
                    messageService.insertMessage(message);
                }
                Message message = new Message();
                message.setTitle("Rating更新");
                message.setTargetuid(u.getUid());
                message.setSenduid(-1);
                message.setDate(new Date());
                message.setContent("您因参加了比赛"+contest.getTitle()+"，Rating由"+pre+"变成了"+u.getRating());
                messageService.insertMessage(message);
                userService.updateUserInfo(u);
            }
        }
        contest.setStatus(1);
        contestService.updateContest(contest);
        ret.put("state",0);
        return ret;
    }

    private List<Map<Integer,Integer>> getRatingChange(Integer cid) { // T(O(N*N*log(N)) + O(N*N) + O(N) + O(C) + C)
        List<Contestregister> rankList = contestService.findRestersAndRankNotNullOnContestByCid(cid);
        List<Map<String,Object>> tmp = new ArrayList<>();
        long num = rankList.size();
        for (Contestregister contestregister: rankList) {
            Map<String,Object> tp = new HashMap<>();
            if (contestregister.getStatus() == 1 && contestregister.getRole() == 0) {
                tp.put("uid", contestregister.getUid());
                tp.put("rank", contestregister.getRank());
                tp.put("rating", contestregister.getLastrating()<=0?1000:contestregister.getLastrating());
                Map<String, Object> ttp = calcKRatio(contestregister.getUid());
                tp.put("ratio", ttp.get("K"));
                tp.put("num", ttp.get("cnt"));
                tmp.add(tp);
            }
        }

        Map<pers.dreamer.util.Pair<Integer, Integer>,Double> E = new HashMap<>(); //存储胜率期望 E<Aid -> Bid>
        for(int i = 0;i < tmp.size()-1;i++)
            for(int j = i + 1;j < tmp.size();j++){
                double e = 1.0 / (1 + Math.pow(10, ((Integer) tmp.get(j).get("rating") - (Integer) tmp.get(i).get("rating"))/400.0));
                E.put(new pers.dreamer.util.Pair<>((Integer) tmp.get(j).get("uid"),(Integer) tmp.get(i).get("uid")),1.0-e);
            }

        Map<Integer,Double> Et = new HashMap<>(); //存储排名期望Er<Aid>

        for(int i = 0;i < tmp.size()-1;i++)
            for (int j = i+1; j < tmp.size(); j++) {
                Et.put((Integer) tmp.get(i).get("uid"),Et.getOrDefault(tmp.get(i).get("uid"),1.0)+E.get(new pers.dreamer.util.Pair<>((Integer) tmp.get(j).get("uid"),(Integer) tmp.get(i).get("uid"))));
                Et.put((Integer) tmp.get(j).get("uid"),Et.getOrDefault(tmp.get(j).get("uid"),1.0)+(1.0-E.get(new pers.dreamer.util.Pair<>((Integer) tmp.get(j).get("uid"),(Integer) tmp.get(i).get("uid")))));
            }

        Map<Integer,Double> m = new HashMap<>(); //存储期望排名Et和实际排名T的几何平均m<Aid>

        for (Map<String,Object> i:tmp) {
            m.put((Integer) i.get("uid"),Math.sqrt(Et.get((Integer) i.get("uid")) * (Integer) i.get("rank")));
        }

        Map<Integer,Double> Er = new HashMap<>(); //存储积分期望Er<Aid>

        for (Map<String,Object> i:tmp) {
            double mi = m.get((Integer) i.get("uid")) - 1.0;
            double l = .0, r = 4000.0;
            while (r-l > 1e-3) { // 二分快速确定er
                double mid = (l+r)/2.0;
                double v = .0;
                for (Map<String,Object> j:tmp) {
                    if (((Integer) j.get("uid")).equals((Integer) i.get("uid"))) {
                        continue;
                    }
                    v += 1 / (1 + Math.pow(10,(mid - (Integer) j.get("rating"))/400.0));
                }
                if (v < mi) {
                    r = mid;
                } else {
                    l = mid;
                }
            }
            Er.put((Integer) i.get("uid"),l);
        }

        List<Map<Integer,Integer>> ret = new ArrayList<>(); // 返回结果[{UID:newScore},]

        for (Map<String,Object> i:tmp) {
            Map<Integer,Integer> t = new HashMap<>();
            double Fk = 0.9 * ((Double) i.get("ratio") / ((Integer) i.get("rank") / (1.0*num) )) * (1.8 - MyUtil.getInfty((Integer) i.get("num")+1));
            double er = (Integer) i.get("rating") + Fk * (Er.get((Integer) i.get("uid")) - (Integer) i.get("rating"));

            t.put((Integer) i.get("uid"), (int) Math.ceil(er));
            ret.add(t);
        }
        return ret;
    }

    private Map<String,Object> calcKRatio(Integer uid) {
        Map<String,Object> ret = new HashMap<>();
        List<Contestregister> lists = contestService.findRestersOnContestByUid(uid);
        Double ans = 0.0;
        Integer cnt = 0;
        for (Contestregister contestregister: lists) {
                long num = contestService.findFormalContestRegisterBy(contestregister.getCid());
                ans += (1.0 * contestregister.getRank()) / num;
                cnt++;
        }
        ret.put("cnt",cnt);
        if (cnt != 0) {
            ret.put("K",ans/cnt);
        } else {
            ret.put("K",1.0);
        }
        return ret;
    }

    private void submitICPC(Integer uid, Integer cid, CodeDto codeDto) {
        Integer sid = submitlistService.insertCodeAndSubmit(codeDto,uid,cid);
        execCode(sid);
    }

    private void submitOI(Integer uid, Integer cid, CodeDto codeDto) {
        Submitlist submitlist = submitlistService.findSubmitlistByCidAndUidAndPid(cid,uid,codeDto.getPid());
        if (submitlist == null) {
            submitlistService.insertCodeAndSubmit(codeDto,uid,cid);
        } else {
            Code code = submitlistService.findCodeByCodeid(submitlist.getSid());
            code.setText(codeDto.getCode());
            submitlistService.updateCode(code);
            submitlist.setSize(MyUtil.codeDecode(codeDto.getCode()).length());
            submitlist.setLang(codeDto.getLang());
            submitlistService.updateSubmitlistBySid(submitlist);
        }
    }

    private void submitIOI(Integer uid, Integer cid, CodeDto codeDto) {
        submitICPC(uid,cid,codeDto);
    }

    private void submitShortCode(Integer uid, Integer cid, CodeDto codeDto) {
        Submitlist submitlist = submitlistService.findSubmitlistByCidAndUidAndPid(cid,uid,codeDto.getPid());
        Integer sid;
        if (submitlist == null) {
            sid = submitlistService.insertCodeAndSubmit(codeDto,uid,cid);
        } else {
            Code code = submitlistService.findCodeByCodeid(submitlist.getSid());
            code.setText(codeDto.getCode());
            submitlistService.updateCode(code);

            submitlist.setTimeUsed(null);
            submitlist.setMemoryUsed(null);
            submitlist.setResult(7);
            submitlist.setMsg("");
            submitlist.setSize(MyUtil.codeDecode(codeDto.getCode()).length());
            submitlist.setLang(codeDto.getLang());
            submitlistService.updateSubmitlistBySid(submitlist);

            sid = submitlist.getSid();
        }
        execCode(sid);
    }

    private void submitMinTime(Integer uid, Integer cid, CodeDto codeDto) {
        Submitlist submitlist = submitlistService.findSubmitlistByCidAndUidAndPid(cid,uid,codeDto.getPid());
        Integer sid;
        if (submitlist == null) {
            sid = submitlistService.insertCodeAndSubmit(codeDto,uid,cid);
        } else {
            Code code = submitlistService.findCodeByCodeid(submitlist.getSid());
            code.setText(codeDto.getCode());
            submitlistService.updateCode(code);

            submitlist.setTimeUsed(null);
            submitlist.setMemoryUsed(null);
            submitlist.setResult(7);
            submitlist.setMsg("");
            submitlist.setSize(MyUtil.codeDecode(codeDto.getCode()).length());
            submitlist.setLang(codeDto.getLang());
            submitlistService.updateSubmitlistBySid(submitlist);

            sid = submitlist.getSid();
        }
        execCode(sid);
    }

    private Map<String,Object> rankICPC(Contest contest) {
        Map<String,Object> ret = new HashMap<>();
        List<RankDto> rankDtos = new ArrayList<>();
        Map<Integer,Integer> t = new HashMap<>();
        List<Contestsubmitinfo> contestsubmitinfos = contestService.findContestsubmitinfoByCid(contest.getCid());
        List<Contestproblems> contestproblems = contestService.findProblemsNumByCid(contest.getCid());
        for (Contestproblems contestproblems1:contestproblems) {
            t.put(contestproblems1.getPid(),contestproblems1.getNum());
        }
        int nowUid = -1;
        int preUid = -1;
        int tot = -1;
        long[] fb = new long[contestproblems.size()];
        long[] fbu = new long[contestproblems.size()];
        long startTime = contest.getStarttime().getTime();
        for (Contestsubmitinfo contestsubmitinfo:contestsubmitinfos) {
            Integer n = t.get(contestsubmitinfo.getPid());
            if (n == null) continue;
            nowUid = contestsubmitinfo.getUid();
            if (nowUid != preUid) {
                tot++;
                RankDto rankDto = new RankDto(contestproblems.size());
                rankDto.setName(UserUtil.getStyleNameByUid(nowUid,userService,achievementService));
                rankDto.setUid(nowUid);
                rankDto.setState(contestService.findRegisterStateByCidAndUid(contest.getCid(),nowUid));
                rankDtos.add(rankDto);
                preUid = nowUid;
            }
            if (contestsubmitinfo.getResult() == 0) {
                long time = contestsubmitinfo.getAcTime().getTime();
                if (fb[n] == 0 || time < fb[n]) {
                    fb[n] = time;
                    fbu[n] = nowUid;
                }
                rankDtos.get(tot).addData(n,(time-startTime)/1000,contestsubmitinfo.getCnt(),0);
            } else {
                rankDtos.get(tot).addData(n,0,contestsubmitinfo.getCnt(),1);
            }
        }
        ret.put("fb",fbu);
        ret.put("set",rankDtos);
        return ret;
    }

    private Map<String, Object> rankOI(Contest contest) {
        Map<String,Object> tmp = new HashMap<>();
        List<RankDto> rankDtos = new ArrayList<>();
        Map<Integer,Integer> t = new HashMap<>();
        List<Contestsubmitinfo> contestsubmitinfos = contestService.findContestsubmitinfoByCid(contest.getCid());
        List<Contestproblems> contestproblems = contestService.findProblemsNumByCid(contest.getCid());
        for (Contestproblems contestproblems1:contestproblems) {
            t.put(contestproblems1.getPid(),contestproblems1.getNum());
        }
        int nowUid = -1;
        int preUid = -1;
        int tot = -1;
        long startTime = contest.getStarttime().getTime();
        for (Contestsubmitinfo contestsubmitinfo:contestsubmitinfos) {
            Integer n = t.get(contestsubmitinfo.getPid());
            if (n == null) continue;
            nowUid = contestsubmitinfo.getUid();
            if (nowUid != preUid) {
                tot++;
                RankDto rankDto = new RankDto(contestproblems.size());
                rankDto.setName(UserUtil.getStyleNameByUid(nowUid,userService,achievementService));
                rankDto.setUid(nowUid);
                rankDto.setState(contestService.findRegisterStateByCidAndUid(contest.getCid(),nowUid));
                rankDtos.add(rankDto);
                preUid = nowUid;
            }
            long time = contestsubmitinfo.getAcTime().getTime();
            rankDtos.get(tot).addOIData(n,(time-startTime)/1000,contestsubmitinfo.getScore(),0);
        }
        tmp.put("set",rankDtos);
        return tmp;
    }

    private Map<String, Object> rankIOI(Contest contest) {
        return rankOI(contest);
    }

    private Map<String, Object> rankShortCode(Contest contest) {
        Map<String,Object> tmp = new HashMap<>();
        List<RankDto> rankDtos = new ArrayList<>();
        Map<Integer,Integer> t = new HashMap<>();
        List<Contestsubmitinfo> contestsubmitinfos = contestService.findContestsubmitinfoByCid(contest.getCid());
        List<Contestproblems> contestproblems = contestService.findProblemsNumByCid(contest.getCid());
        for (Contestproblems contestproblems1:contestproblems) {
            t.put(contestproblems1.getPid(),contestproblems1.getNum());
        }
        int nowUid = -1;
        int preUid = -1;
        int tot = -1;
        long startTime = contest.getStarttime().getTime();
        int[] fb = new int[contestproblems.size()];
        int[] fbu = new int[contestproblems.size()];
        for (Contestsubmitinfo contestsubmitinfo:contestsubmitinfos) {
            Integer n = t.get(contestsubmitinfo.getPid());
            if (n == null) continue;
            nowUid = contestsubmitinfo.getUid();
            if (nowUid != preUid) {
                tot++;
                RankDto rankDto = new RankDto(contestproblems.size());
                rankDto.setName(UserUtil.getStyleNameByUid(nowUid,userService,achievementService));
                rankDto.setUid(nowUid);
                rankDto.setState(contestService.findRegisterStateByCidAndUid(contest.getCid(),nowUid));
                rankDtos.add(rankDto);
                preUid = nowUid;
            }
            if (contestsubmitinfo.getResult() == 0) {
                if (fb[n] == 0 || contestsubmitinfo.getScore().intValue() < fb[n]) {
                    fb[n] = contestsubmitinfo.getScore().intValue();
                    fbu[n] = contestsubmitinfo.getUid();
                }
                rankDtos.get(tot).addSCData(n,contestsubmitinfo.getScore().intValue(),0);
            } else {
                rankDtos.get(tot).addSCData(n,contestsubmitinfo.getScore().intValue(),1);
            }
        }
        tmp.put("fb",fbu);
        tmp.put("set",rankDtos);
        return tmp;
    }

    private Map<String, Object> rankMinTime(Contest contest) {
        return rankShortCode(contest);
    }
}
