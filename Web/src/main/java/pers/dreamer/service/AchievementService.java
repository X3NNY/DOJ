package pers.dreamer.service;

import pers.dreamer.bean.Achievement;
import pers.dreamer.bean.Achievementlist;

import java.util.List;

public interface AchievementService {
    Achievement findAchiByAid(Integer aid);

    List<Achievementlist> findAllAchisByUid(Integer uid);

    boolean checkUserOwnsAchiByUidAndAid(Integer uid, Integer aid);

    String findAchiTitleByAid(Integer aid);
}
