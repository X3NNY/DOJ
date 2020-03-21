package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Problemvote;
import pers.dreamer.bean.ProblemvoteExample;

public interface ProblemvoteMapper {
    long countByExample(ProblemvoteExample example);

    int deleteByExample(ProblemvoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Problemvote record);

    int insertSelective(Problemvote record);

    List<Problemvote> selectByExample(ProblemvoteExample example);

    Problemvote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Problemvote record, @Param("example") ProblemvoteExample example);

    int updateByExample(@Param("record") Problemvote record, @Param("example") ProblemvoteExample example);

    int updateByPrimaryKeySelective(Problemvote record);

    int updateByPrimaryKey(Problemvote record);

    @Select("select vote from problemvote where pid=#{pid} and uid=#{uid} limit 0,1")
    Integer findVoteByPidAndUid(@Param("pid") Integer pid,@Param("uid") Integer uid);
}