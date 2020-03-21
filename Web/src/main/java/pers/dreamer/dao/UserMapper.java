package pers.dreamer.dao;

import java.beans.Transient;
import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.User;
import pers.dreamer.bean.UserExample;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select count(uid)+1 from `user` where rating > #{rating}")
    Long selectRankByRating(@Param("rating") Integer rating);

    @Select("select username,rating,preaid,sufaid from `user` where uid=#{uid}")
    User findUserStyleNameByUid(Integer uid);

    @Select("select uid from `user` where username=#{name}")
    Integer findUidByUsername(String name);

    @Select("update `user` set level=level+${i},liveness=liveness+${i} where uid=#{uid}")
    void addLivenessAndLevelByUid(@Param("uid") Integer uid,@Param("i") Integer i);

    @Select("update `user` set liveness=liveness+${i} where uid=#{uid}")
    void addLivenessByUid(@Param("uid") Integer uid,@Param("i") Integer i);

    @Select("select username from `user` where uid=#{uid} limit 0,1")
    String findUsernameByUid(Integer uid);

    @Select("select count(*) from `user` where uid=#{uid}")
    Integer isExistByUid(Integer uid);

    @Select("select rating from `user` where uid=#{uid} limit 0,1")
    Integer findRatingByUid(Integer uid);

    @Select("select uid,liveness,coins from `user` where liveness >= #{limit}")
    List<User> findAllUsersByLiveness(Integer limit);

    @Select("select uid,username,email from `user`")
    List<User> findEmailAndName();

    @Select("select uid,liveness,coins,level from `user` where uid=#{uid} order by uid limit 0,1")
    User findCoinsAndLiveAndLevelByUid(Integer uid);

    void updateByUser(User user);
}