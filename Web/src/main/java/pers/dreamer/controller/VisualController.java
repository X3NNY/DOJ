package pers.dreamer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dreamer.bean.Submitlist;
import pers.dreamer.bean.User;
import pers.dreamer.dto.SubmitDto;
import pers.dreamer.service.BlogService;
import pers.dreamer.service.SubmitlistService;
import pers.dreamer.service.UserService;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@CrossOrigin
public class VisualController {
    @Autowired
    UserService userService;

    @Autowired
    SubmitlistService submitlistService;

    @Autowired
    BlogService blogService;

    @GetMapping("/api/visual/index/submit")
    public Map<String,Object> getIndexSubmitStatistics() {
        Map<String,Object> ret = new HashMap<>();
        List<List<Long>> list = new ArrayList<>();
        List<SubmitDto> submitDtos = submitlistService.findAllSubmitlistLast30Days();
        processData(list, submitDtos);
        ret.put("submit",list);
        list = new ArrayList<>();
        submitDtos = submitlistService.findAcSubmitlistLast30Days();
        processData(list, submitDtos);
        ret.put("ac",list);
        return ret;
    }

    private void processData(List<List<Long>> list, List<SubmitDto> submitDtos) {
        for (SubmitDto submitDto:submitDtos) {
            List<Long> tp = new ArrayList<>();
            tp.add(submitDto.getDate().getTime()+1000L*60*60*8);
            tp.add(submitDto.getNum());
            list.add(tp);
        }
    }

    @GetMapping("/api/visual/user/rating")
    public Map<String, Object> getUserRatingStatistics(Integer uid) {
        Map<String,Object> ret = new HashMap<>();
        User user = userService.findUserByUid(uid);
        if (user == null) {
            ret.put("state",1);
            return ret;
        }

        ret.put("state",0);
        ret.put("username",user.getUsername());
        ret.put("contest",userService.findContestByUid(user.getUid()));
        return ret;
    }

    @GetMapping("/api/visual/user/submit")
    public Map<String, List<List<Long>>> getUserSubmitStatistics(Integer uid, String username) throws UnsupportedEncodingException {
        Map<String,List<List<Long>>> ret = new HashMap<>();
        username = new String(username.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
        Integer u = uid;
        if (!userService.isExistUserByUid(uid)) {
            u = userService.findUidByUsername(username);
        }
        if (u == null) return null;
        List<List<Long>> list = new ArrayList<>();
        List<SubmitDto> submitDtos = submitlistService.findAllSubmitlistLast30DaysByUid(u);
        processData(list, submitDtos);
        ret.put("submit",list);
        list = new ArrayList<>();
        submitDtos = submitlistService.findAcSubmitlistLast30DaysByUid(u);
        processData(list, submitDtos);
        ret.put("ac",list);
        return ret;
    }

    @GetMapping("/api/visual/user/allpass")
    public List<Long> getUserAllPassProblemStatistics(Integer uid, String username) {
        Integer u = uid;
        if (!userService.isExistUserByUid(uid)) {
            u = userService.findUidByUsername(username);
        }
        if (u == null) return null;
        List<Long> dates = submitlistService.findAllPassDateOnProblemByUid(u);
        return dates;
    }

    @GetMapping("/api/visual/admin/register")
    public List<List<Long>> getRegisterStatistics() {
        List<List<Long>> ret = new ArrayList<>();
        List<SubmitDto> dates = submitlistService.findRegisterLast30Days();
        processData(ret,dates);
        return ret;
    }

    @GetMapping("/api/visual/admin/active")
    public Map<String,List<List<Long>>> getActiveStatistics() {
        return null;
    }
}
