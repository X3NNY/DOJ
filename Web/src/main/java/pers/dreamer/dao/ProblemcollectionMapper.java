package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Problemcollection;
import pers.dreamer.bean.ProblemcollectionExample;

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

    @Select("select pid from problemcollection where uid=#{uid}")
    List<Integer> findPidByUid(Integer uid);
}