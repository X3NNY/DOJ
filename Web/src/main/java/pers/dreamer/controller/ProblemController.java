package pers.dreamer.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.*;
import pers.dreamer.dto.ProblemStatusDto;
import pers.dreamer.dto.TagDto;
import pers.dreamer.service.*;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.idworker.Sid;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.*;

/**
 * 题目界面
 */
@RestController
@CrossOrigin
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    DateFormat dateFormat;

    @Autowired
    ProbleminfoService probleminfoService;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    ProblemcollectionService problemcollectionService;

    @Autowired
    BlogService blogService;

    @Autowired
    Sid sid;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AchievementService achievementService;

    @GetMapping("/api/problem")
    public Map<String,Object> getProblem(Integer pid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Problem problem = problemService.findProblemByPid(pid,user);
        if(problem != null){
            ret.put("pid",problem.getPid());
            ret.put("title",problem.getTitle());
            ret.put("input",problem.getInput());
            ret.put("output",problem.getOutput());
            ret.put("sampleoutput",problem.getSampleoutput());
            ret.put("sampleinput",problem.getSampleinput());
            ret.put("desc",problem.getDescription());
            ret.put("hint",problem.getHint());
            ret.put("timelimit",problem.getTimelimit());
            ret.put("memorylimit",problem.getMemorylimit());
            ret.put("username",UserUtil.getStyleNameByUid(problem.getUid(),userService,achievementService));
            ret.put("type",problem.getType());
            ret.put("special",problem.getSpecial());
            ret.put("state",user==null?0:submitlistService.findStatusOnProblemByUidAndPid(user.getUid(), pid));
            ret.put("collect",user==null?false:problemcollectionService.isUserCollect(pid,user.getUid()));
        }
        return ret;
    }

    @GetMapping("/api/problem/discuss/{pid}")
    public List<Map<String,Object>> getComment(@PathVariable("pid") Integer pid,HttpSession session){
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        List<Blog> blogs = blogService.findBlogsByProblem("#P"+pid+"|");
        for (Blog blog: blogs) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("bid",blog.getBid());
            tmp.put("uid",blog.getUid());
            tmp.put("title",blog.getTitle());
            tmp.put("username", UserUtil.getStyleNameByUid(blog.getUid(),userService,achievementService));
            tmp.put("date",blog.getDate());
            tmp.put("up_vote",blog.getUpVote());
            tmp.put("down_vote",blog.getDownVote());
            Integer myVote = blogService.findMyVoteByBidAndUid(blog.getBid(),user==null?null:user.getUid());
            tmp.put("my_vote",myVote);
            tmp.put("state",user==null?0:user.getUid().equals(blog.getUid()));
            ret.add(tmp);
        }
        return  ret;
    }

    @PostMapping("/api/problem/changestatus")
    public Map<String,Integer> changeProblemStatus(Integer pid, Integer status,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 3) {
            ret.put("state",1);
        } else {
            Problem problem = problemService.findProblemByPid(pid,user);
            problem.setStatus(status>0?1:0);
            problemService.updateProblem(problem);
            ret.put("state",0);
        }
        return ret;
    }

    @GetMapping("/api/problem/tags")
    public List<Tags> getTags() {
        return problemService.findTags();
    }

    @GetMapping("/api/problem/info/{pid}")
    public Map<String,Object> getProblemInfo(@PathVariable("pid") Integer pid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        ret.put("pass_num",probleminfoService.findPass_numByPid(pid));
        ret.put("submit_num",probleminfoService.findSubmit_numByPid(pid));
        Probleminfo probleminfo = probleminfoService.findProblemInfoByPid(pid);
        if(probleminfo != null){
            ret.put("ac_cnt",probleminfo.getAcCnt());
            ret.put("wa_cnt",probleminfo.getWaCnt());
            ret.put("tle_cnt",probleminfo.getTleCnt());
            ret.put("mle_cnt",probleminfo.getMleCnt());
            ret.put("re_cnt",probleminfo.getReCnt());
            ret.put("se_cnt",probleminfo.getSeCnt());
            ret.put("pe_cnt",probleminfo.getPeCnt());
            ret.put("ole_cnt",probleminfo.getOleCnt());
            ret.put("ce_cnt",probleminfo.getCeCnt());
        }
        List<Problemtag> problemtags = problemService.findProblemtagsUidByPid(pid);
        List<TagDto> tagDtos = new ArrayList<>();
        Map<Integer,String> t = new HashMap<>();
        int nowTid = -1;
        int preTid = -1;
        int tot = -1;
        for (Problemtag problemtag:problemtags) {
            nowTid = problemtag.getTid();
            if (nowTid != preTid) {
                TagDto tagDto = new TagDto();
                tagDto.setTag(problemService.findTagnameByTid(problemtag.getTid()));
                tagDto.setCnt(0);
                tagDto.setState(problemtag.getUid().equals(user.getUid()) ?1:0);
                tagDtos.add(tagDto);
                tot++;
            }
            tagDtos.get(tot).addCnt();
        }
        ret.put("tag",tagDtos);
        return  ret;
    }

    @PostMapping("/api/problem/new")
    public Map<String, Object> addProblems(@RequestBody Problem problem, HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        ret.put("pid",-1);
        User user = (User) session.getAttribute("user");
        if(user == null || user.getRole() < 2) ret.put("state",1);
        else{
            problem.setUid(user.getUid());
            problem.setType(0);
            problem.setStatus(1);
            problem.setSpecial(0);
            boolean f = problemService.insertProblem(problem);
            if(f){
                problem = problemService.findProblemByTitle(problem.getTitle());
                probleminfoService.insertProblemInfoByPid(problem.getPid());
                ret.put("pid",problem.getPid());
                user.setLevel(user.getLevel()+20);
                user.setLiveness(user.getLiveness()+20);
                userService.updateUserInfo(user);
            }else ret.put("state",1);

        }
        return ret;
    }

    @PostMapping("/api/problem/add/tag")
    public Map<String,Object> addTagsForUser(Integer pid,Integer tid,HttpSession session){
        Map<String, Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null || !problemService.isExistTag(tid)) {
            ret.put("state",1);
        } else if (submitlistService.findStatusOnProblemByUidAndPid(user.getUid(),pid) != 1) {
            ret.put("state",2);
        } else {
            userService.addLivenessAndLevelByUid(user.getUid(),10);
            Problemtag problemtag = new Problemtag();
            problemtag.setTid(tid);
            problemtag.setDate(new Date());
            problemtag.setPid(pid);
            problemtag.setUid(user.getUid());
            boolean f = problemService.insertProblemTag(problemtag);
            if(!f) ret.put("state",3);
            else ret.put("state",0);
        }
        return ret;
    }

    @GetMapping("/api/problem/getcollect")
    public List<Map<String,Object>> getProCollect(Integer uid) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = userService.findUserByUid(uid);
        if (user == null) {
            return ret;
        }
        List<Problemcollection> problemcollections = problemcollectionService.findCollectsByUid(uid);
        for (Problemcollection problemcollection:problemcollections) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("id",problemcollection.getId());
            tmp.put("pid",problemcollection.getPid());
            tmp.put("title",problemService.findProblemByPid(problemcollection.getPid(),user).getTitle());
            tmp.put("date",problemcollection.getDate());
            ret.add(tmp);
        }
        return ret;
    }

    @PostMapping("/api/problem/collect")
    public Map<String, Object> collectProblemByUser(Integer pid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else problemService.changeProblemCollectionByPidAndUid(pid,user.getUid());
        return ret;
    }

    @DeleteMapping("/api/problem/del/tag")
    public Map<String,Object> delTagsByPid(@RequestBody TagDto tagDto,Integer pid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        if(user == null) ret.put("state",1);
        else if(!problemService.delTagsByUidAndPidAndTag(user.getUid(),pid,tagDto.getTag())) ret.put("state",1);
        return ret;
    }


    @PutMapping("/api/problem/edit")
    public Map<String,Object> editProblem(@RequestBody Problem problem, Integer pid,HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        Problem problem1 = problemService.findProblemByPid(pid,user);
        problem1.setTitle(problem.getTitle());
        problem1.setDescription(problem.getDescription());
        problem1.setInput(problem.getInput());
        problem1.setOutput(problem.getOutput());
        problem1.setSampleinput(problem.getSampleinput());
        problem1.setSampleoutput(problem.getSampleoutput());
        problem1.setHint(problem.getHint());
        problem1.setTimelimit(problem.getTimelimit());
        problem1.setMemorylimit(problem.getMemorylimit());
        problem1.setType(problem.getType()%4);
        problem1.setSpecial(problem.getSpecial());
        if(user == null || (user.getRole()<5 && !problem1.getUid().equals(user.getUid()))) ret.put("state",1);
        else if(!problemService.editProblem(problem1)) ret.put("state",1);
        return ret;
    }

    @PutMapping("/api/problem/update/status")
    public Map<String,Object> updateProblemStatusByPid(@RequestBody ProblemStatusDto problemStatusDto, Integer pid, HttpSession session){
        Map<String,Object> ret = new HashMap<>();
        ret.put("state",0);
        User user = (User) session.getAttribute("user");
        problemStatusDto.setPid(pid);
        if(user == null||user.getRole() < 7) ret.put("state",1);
        else if(!problemService.updateProblemStatusByPidAndStatus(problemStatusDto.getPid(),problemStatusDto.getStatus())) ret.put("state",1);
        return ret;
    }
}
