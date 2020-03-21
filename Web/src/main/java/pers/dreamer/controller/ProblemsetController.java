package pers.dreamer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dreamer.bean.*;
import pers.dreamer.service.*;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.tools.MyUtil;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

@RestController
@CrossOrigin
public class ProblemsetController {

    @Autowired
    ProblemService problemService;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    ProbleminfoService probleminfoService;

    @Autowired
    UserService userService;

    @Autowired
    AchievementService achievementService;

    @GetMapping("/api/problem/getpages")
    public Map<String,Object> getProblemPage(String name,String tag,Integer star,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();

        User user = (User) session.getAttribute("user");
        Long num = problemService.findProblemsSizeByPageAndNameAndTagAndStar(name,tag,star,user);
        ret.put("pages",( num + 49) / 50);
        return ret;
    }


    @GetMapping("/api/problem/toindex")
    public List<Map<String,Object>> getProblemsToIndex(HttpSession session) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        List<Problem> problems = problemService.findProblemsToIndex();
        boolean isUserNull = user == null;
        for(Problem problem : problems){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("pid",problem.getPid());
            tmp.put("title",problem.getTitle());
            tmp.put("state",user != null ? submitlistService.findStatusOnProblemByUidAndPid(user.getUid(),problem.getPid()):0);
            Probleminfo probleminfo = probleminfoService.findProblemInfoByPid(problem.getPid());
            tmp.put("ac_cnt",probleminfo != null ? probleminfo.getAcCnt() : 0);
            tmp.put("all_cnt",probleminfo!=null?probleminfo.getAcCnt()
                    +probleminfo.getCeCnt()
                    +probleminfo.getMleCnt()
                    +probleminfo.getOleCnt()
                    +probleminfo.getReCnt()
                    +probleminfo.getSeCnt()
                    +probleminfo.getTleCnt()
                    +probleminfo.getWaCnt()
                    +probleminfo.getPeCnt():0);
            tmp.put("author",UserUtil.getStyleNameByUid(problem.getUid(),userService,achievementService));
            tmp.put("up_vote",probleminfo != null ? probleminfo.getUpVote() : 0);
            tmp.put("down_vote",probleminfo != null ? probleminfo.getDownVote():0);
            tmp.put("my_vote",isUserNull? 0:problemService.findProblemVoteByUidAndPid(user.getUid(),problem.getPid()));
            ret.add(tmp);
        }
        return ret;
    }

    @GetMapping("/api/problem/set")
    public List<Map<String,Object>> getProblemsetByLimit(Integer page,String name,String tag,Integer star,HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if(name == null || name.equals("")){
            if(user != null && user.getRole() >= 7) name = "";
            else name = null;
        }
        List<Problem> problems = problemService.findProblemsByPageAndNameAndTagAndStar(page==null||page.compareTo(0) <= 0?0:page-1,50,name,tag,star,user);
        boolean isUserNull = user == null;
        for(Problem problem : problems){
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("pid",problem.getPid());
            tmp.put("title",problem.getTitle());
            tmp.put("state",isUserNull ? 0:submitlistService.findStatusOnProblemByUidAndPid(user.getUid(),problem.getPid()));
            Probleminfo probleminfo = probleminfoService.findProblemInfoByPid(problem.getPid());
            tmp.put("ac_cnt",probleminfo != null ? probleminfo.getAcCnt() : 0);
            tmp.put("all_cnt",probleminfo!=null?probleminfo.getAcCnt()
                    +probleminfo.getCeCnt()
                    +probleminfo.getMleCnt()
                    +probleminfo.getOleCnt()
                    +probleminfo.getReCnt()
                    +probleminfo.getSeCnt()
                    +probleminfo.getTleCnt()
                    +probleminfo.getWaCnt()
                    +probleminfo.getPeCnt():0);
            tmp.put("author", UserUtil.getStyleNameByUid(problem.getUid(),userService,achievementService));
            tmp.put("up_vote",probleminfo != null ? probleminfo.getUpVote() : 0);
            tmp.put("down_vote",probleminfo != null ? probleminfo.getDownVote():0);
            tmp.put("status",problem.getStatus());
            tmp.put("my_vote",isUserNull? 0:problemService.findProblemVoteByUidAndPid(user.getUid(),problem.getPid()));
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/problem/upvote")
    public Map<String,Integer> upVote(Integer pid, HttpSession session){
        Map<String,Integer> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else{
            if(problemService.isVoteExist(user.getUid(),pid)) ret.put("state",2);
            else {
                problemService.insertProblemVoteyByUidAndPid(user.getUid(),pid,1);
                probleminfoService.upVote(pid);
                Integer uid = problemService.findAuthorIdByPid(pid);
                userService.addLivenessByUid(uid,5);
            }
        }
        return ret;
    }

    @PostMapping("/api/problem/downvote")
    public Map<String,Integer> downVote(Integer pid, HttpSession session){
        Map<String,Integer> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else{
            if(problemService.isVoteExist(user.getUid(),pid)) ret.put("state",2);
            else {
                problemService.insertProblemVoteyByUidAndPid(user.getUid(),pid,-1);
                probleminfoService.downVote(pid);
            }
        }
        return ret;
    }
}
