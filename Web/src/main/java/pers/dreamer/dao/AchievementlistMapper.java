package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Achievementlist;
import pers.dreamer.bean.AchievementlistExample;

public interface AchievementlistMapper {
    long countByExample(AchievementlistExample example);

    int deleteByExample(AchievementlistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Achievementlist record);

    int insertSelective(Achievementlist record);

    List<Achievementlist> selectByExample(AchievementlistExample example);

    Achievementlist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Achievementlist record, @Param("example") AchievementlistExample example);

    int updateByExample(@Param("record") Achievementlist record, @Param("example") AchievementlistExample example);

    int updateByPrimaryKeySelective(Achievementlist record);

    int updateByPrimaryKey(Achievementlist record);
}