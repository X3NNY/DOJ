package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dreamer.bean.Achievement;
import pers.dreamer.bean.Achievementlist;
import pers.dreamer.bean.AchievementlistExample;
import pers.dreamer.dao.AchievementMapper;
import pers.dreamer.dao.AchievementlistMapper;
import pers.dreamer.service.AchievementService;

import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    AchievementMapper achievementMapper;

    @Autowired
    AchievementlistMapper achievementlistMapper;

    @Override
    public Achievement findAchiByAid(Integer aid) {
        Achievement achievement = achievementMapper.selectByPrimaryKey(aid);
        return achievement==null?new Achievement():achievement;
    }

    @Override
    public List<Achievementlist> findAllAchisByUid(Integer uid) {
        return achievementMapper.selectAllAchisByUid(uid);
    }

    @Override
    public boolean checkUserOwnsAchiByUidAndAid(Integer uid, Integer aid) {
        List<Integer> aids = achievementMapper.checkOwnsByUidAndAid(uid,aid);
        return !aids.isEmpty();
    }

    @Override
    public String findAchiTitleByAid(Integer aid) {
        return achievementMapper.findTitleByAid(aid);
    }
}
