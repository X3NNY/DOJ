package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import pers.dreamer.bean.Achievement;
import pers.dreamer.bean.AchievementExample;
import pers.dreamer.bean.Achievementlist;

public interface AchievementMapper {
    long countByExample(AchievementExample example);

    int deleteByExample(AchievementExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(Achievement record);

    int insertSelective(Achievement record);

    List<Achievement> selectByExample(AchievementExample example);

    Achievement selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("record") Achievement record, @Param("example") AchievementExample example);

    int updateByExample(@Param("record") Achievement record, @Param("example") AchievementExample example);

    int updateByPrimaryKeySelective(Achievement record);

    int updateByPrimaryKey(Achievement record);

    @Select("select * from achievementlist where uid=#{uid} and (validtime=-1 or now()<=ADDDATE(date,validtime))")
    List<Achievementlist> selectAllAchisByUid(Integer uid);

    @Select("select aid from achievementlist where uid=#{uid} and aid=#{aid} and (validtime=-1 or now()<=ADDDATE(date,validtime))")
    List<Integer> checkOwnsByUidAndAid(Integer uid, Integer aid);

    @Select("select `title` from achievement where aid=#{aid}")
    String findTitleByAid(Integer aid);
}