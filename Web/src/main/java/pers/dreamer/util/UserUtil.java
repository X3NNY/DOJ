package pers.dreamer.util;

import pers.dreamer.bean.User;
import pers.dreamer.service.AchievementService;
import pers.dreamer.service.UserService;

public class UserUtil {

    public static int getUserLevel(User user) {
        if (user == null) {
            return -1;
        }
        int level = user.getLevel();
        if (level <= 0) {
            return 0;
        } else if (level <= 1000) {
            return 1;
        } else if (level <= 4000) {
            return 2;
        } else if (level <= 10000) {
            return 3;
        } else if (level <= 20000) {
            return 4;
        }
        return 5;
    }

    public static String getStyleNameByUid(Integer uid,UserService userService, AchievementService achievementService) {
        User user = userService.findUserStyleNameByUid(uid);
        if (user == null) {
            return "pre|DOJ|suf|0";
        }
        String pre = achievementService.findAchiTitleByAid(user.getPreaid());
        String suf = achievementService.findAchiTitleByAid(user.getSufaid());
        return pre + "|" + user.getUsername() + "|" + suf + "|" + user.getRating();
    }

    public static String getStyleNameByUser(User user, AchievementService achievementService) {
        if (user == null) {
            return "pre|DOJ|suf|0";
        }
        String pre = achievementService.findAchiTitleByAid(user.getPreaid());
        String suf = achievementService.findAchiTitleByAid(user.getSufaid());
        return pre + "|" + user.getUsername() + "|" + suf + "|" + user.getRating();
    }

    public static String getNoAchiStyleNameByUid(Integer uid, UserService userService) {
        User user = userService.findUserStyleNameByUid(uid);
        if (user == null) {
            return "null|0";
        }
        return user.getUsername()+"|"+user.getRating();
    }
}
