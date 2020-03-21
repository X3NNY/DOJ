package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Problemtag;
import pers.dreamer.bean.ProblemtagExample;

public interface ProblemtagMapper {
    long countByExample(ProblemtagExample example);

    int deleteByExample(ProblemtagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Problemtag record);

    int insertSelective(Problemtag record);

    List<Problemtag> selectByExample(ProblemtagExample example);

    Problemtag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Problemtag record, @Param("example") ProblemtagExample example);

    int updateByExample(@Param("record") Problemtag record, @Param("example") ProblemtagExample example);

    int updateByPrimaryKeySelective(Problemtag record);

    int updateByPrimaryKey(Problemtag record);

    @Select("select DISTINCT pid from problemtag where tid=#{tid}")
    List<Integer> findPidByTagName(Integer tid);

    @Select("select id,uid,tid from problemtag where pid=#{pid} order by tid,id")
    List<Problemtag> findProblemtagsByPid(Integer pid);
}