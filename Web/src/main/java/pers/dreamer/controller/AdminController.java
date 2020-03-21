package pers.dreamer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import pers.dreamer.bean.Contest;
import pers.dreamer.bean.Goods;
import pers.dreamer.bean.Problem;
import pers.dreamer.bean.User;
import pers.dreamer.service.ContestService;
import pers.dreamer.service.GoodsService;
import pers.dreamer.service.ProblemService;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin
public class AdminController {

    @Autowired
    ProblemService problemService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ContestService contestService;

    @GetMapping("/module/admin/main/info")
    public String loadInfoMain(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user==null || user.getRole()<2) {
            return "admin/forbidden-panel";
        }
        return "admin/main/info";
    }

    @GetMapping("/module/admin/main/setting")
    public String loadSettingMain(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user==null || user.getRole()<6) {
            return "admin/forbidden-panel";
        }
        return "admin/main/setting";
    }

    @GetMapping("/module/admin/problem/my")
    public String loadMyProblem(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user==null || user.getRole()<2) {
            return "admin/forbidden-panel";
        }
        return "admin/problem/myproblem";
    }

    @GetMapping("/module/admin/problem/edit")
    public String loadEditProblem(Integer pid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user==null || user.getRole()<2) {
            return "admin/forbidden-panel";
        }
        Problem problem = problemService.findProblemByPid(pid,user);
        if (problem == null || (problem.getUid() != user.getUid() && user.getRole()<6)) {
            return "admin/forbidden-panel";
        }
        return "admin/problem/editproblem";
    }

    @GetMapping("/module/admin/problem/new")
    public String loadNewProblem(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole()<2) {
            return "admin/forbidden-panel";
        }
        return "admin/problem/newproblem";
    }

    @GetMapping("/module/admin/problem/data")
    public String loadEditProblemData(Integer pid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 2) {
            return "admin/forbidden-panel";
        }
        Problem problem = problemService.findProblemByPid(pid,user);
        if (problem == null || (!problem.getUid().equals(user.getUid()) && user.getRole()<6)) {
            return "admin/forbidden-panel";
        }
        return "admin/problem/editproblemdata";
    }

    @GetMapping("/module/admin/problem/all")
    public String loadAllProblem(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole()<6) {
            return "admin/forbidden-panel";
        }
        return "admin/problem/allproblem";
    }

    @GetMapping("/module/admin/problem/batch")
    public String loadBatchAddProblem(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 2) {
            return "admin/forbidden-panel";
        }
        return "admin/problem/batchproblem";
    }

    @GetMapping("/module/admin/contest/my")
    public String loadMyContest(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 2) {
            return "admin/forbidden-panel";
        }
        return "admin/contest/mycontest";
    }

    @GetMapping("/module/admin/contest/all")
    public String loadAllContest(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/contest/allontest";
    }

    @GetMapping("/module/admin/contest/allclone")
    public String loadAllCloneContest(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/contest/allclonecontest";
    }

    @GetMapping("/module/admin/contest/new")
    public String loadNewContest(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/contest/newcontest";
    }

    @GetMapping("/module/admin/contest/edit")
    public String loadEditContest(Integer cid,HttpSession session) {
        User user = (User) session.getAttribute("user");
        Contest contest = contestService.findContestInfoByCid(cid);
        if (user == null || contest == null || (user.getRole()<6 && !contest.getUid().equals(user.getUid()))) {
            return "admin/forbidden-panel";
        }
        return "admin/contest/editcontest";
    }

    @GetMapping("/module/admin/user/all")
    public String loadAllUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole()<7) {
            return "admin/forbidden-panel";
        }
        return "admin/user/alluser";
    }

    @GetMapping("/module/admin/user/add")
    public String loadAddUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole()<6) {
            return "admin/forbidden-panel";
        }
        return "admin/user/adduser";
    }

    @GetMapping("/module/admin/user/addinvite")
    public String loadAddInviteCode(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/user/addinvite";
    }

    @GetMapping("/module/admin/mall/allgoods")
    public String loadAllGoods(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/mall/allgoods";
    }

    @GetMapping("/module/admin/mall/addgoods")
    public String loadAddGoods(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/mall/addgoods";
    }

    @GetMapping("/module/admin/mall/editgoods")
    public String loadEditGoods(Integer gid,HttpSession session) {
        User user = (User) session.getAttribute("user");
        Goods goods = goodsService.findGoodsByGid(gid);
        if (goods == null || user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/mall/editgoods";
    }

    @GetMapping("/module/admin/mall/allorder")
    public String loadAllOrder(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/mall/allorder";
    }

    @GetMapping("/module/admin/rejudge")
    public String loadReJudge(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return "admin/forbidden-panel";
        }
        return "admin/rejudge";
    }
}
