package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.oj.judge.pojo.Problemcollection;
import pers.dreamer.oj.judge.pojo.ProblemcollectionExample;

public interface ProblemcollectionMapper {
    long countByExample(ProblemcollectionExample example);

    int deleteByExample(ProblemcollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Problemcollection record);

    int insertSelective(Problemcollection record);

    List<Problemcollection> selectByExample(ProblemcollectionExample example);

    Problemcollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Problemcollection record, @Param("example") ProblemcollectionExample example);

    int updateByExample(@Param("record") Problemcollection record, @Param("example") ProblemcollectionExample example);

    int updateByPrimaryKeySelective(Problemcollection record);

    int updateByPrimaryKey(Problemcollection record);
}