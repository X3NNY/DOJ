package pers.dreamer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.*;
import pers.dreamer.service.*;
import pers.dreamer.util.UserUtil;
import pers.dreamer.util.tools.MyUtil;

import javax.servlet.http.HttpSession;
import java.util.*;

@CrossOrigin
@RestController
public class HackController {
    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    HackService hackService;

    @Autowired
    UserService userService;

    @Autowired
    ProblemService problemService;

    @Autowired
    AchievementService achievementService;

    @GetMapping("/api/hack/getpages")
    public Map<String,Long> getHackPages() {
        Map<String,Long> ret = new HashMap<>();
        long num = hackService.findHackTotals();
        ret.put("pages",(num+19)/20);
        return ret;
    }

    @GetMapping("/api/hack/list")
    public List<Map<String,Object>> getHackInfoByPageAndPidAndHackerAndCommitterAndResult(Integer page,Integer pid,String hacker,String committer,Integer result) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User hack,commit;
        if (MyUtil.isNumber(hacker)) {
            hack = userService.findUserByUid(Integer.parseInt(hacker));
        } else {
            hack = userService.findUserByName(hacker);
        }
        if (MyUtil.isNumber(committer)) {
            commit = userService.findUserByUid(Integer.parseInt(committer));
        } else {
            commit = userService.findUserByName(committer);
        }
        if (page==null || page <= 0) page = 1;
        List<Hack> hacks = hackService.findHacksByPageAndPidAndHachAndCommitAndResult(page-1,pid,hack,commit,result);
        for (Hack hack1:hacks) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("id",hack1.getId());
            tmp.put("sid",hack1.getSid());

            tmp.put("hacker", UserUtil.getStyleNameByUid(hack1.getUid(),userService,achievementService));
            Integer uid = submitlistService.findUidBySid(hack1.getSid());
            tmp.put("committer", UserUtil.getStyleNameByUid(uid,userService,achievementService));
            Integer ppid = submitlistService.findPidBySid(hack1.getSid());
            tmp.put("pid",ppid);
            tmp.put("title",problemService.findProblemTitleByPid(ppid));
            tmp.put("result",hack1.getResult());
            tmp.put("date",hack1.getDate());
            ret.add(tmp);
        }
        return ret;
    }

    @GetMapping("/api/hack/info/{id}")
    public Map<String,Object> getHackInfo(@PathVariable("id")Integer id,HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Hack hack = hackService.findHackById(id);
        Submitlist submitlist = submitlistService.findSubmitlistBySid(hack==null?null:hack.getSid());
        if (user == null || hack == null || submitlist == null || (submitlistService.findStatusOnProblemByUidAndPid(user.getUid(),submitlist.getPid()) != 1 && user.getRole()<5)) {
            ret.put("state",1);
            return ret;
        }
        ret.put("state",0);
        ret.put("id",hack.getId());
        ret.put("sid",submitlist.getSid());
        ret.put("pid",submitlist.getPid());
        ret.put("date",hack.getDate());
        ret.put("result",hack.getResult());
        ret.put("answer",hack.getData());
        ret.put("sdate",submitlist.getDate());
        ret.put("filename",hack.getFilename());

        ret.put("ptitle",problemService.findProblemTitleByPid(submitlist.getPid()));

        ret.put("code",submitlistService.findCodeByCodeid(submitlist.getSid()).getText());
        ret.put("hacker",UserUtil.getStyleNameByUid(hack.getUid(),userService,achievementService));
        ret.put("committer",UserUtil.getStyleNameByUid(submitlist.getUid(),userService,achievementService));
        return ret;
    }
}
