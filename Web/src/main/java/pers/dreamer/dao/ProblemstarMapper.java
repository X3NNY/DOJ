package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Problemstar;
import pers.dreamer.bean.ProblemstarExample;

public interface ProblemstarMapper {
    long countByExample(ProblemstarExample example);

    int deleteByExample(ProblemstarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Problemstar record);

    int insertSelective(Problemstar record);

    List<Problemstar> selectByExample(ProblemstarExample example);

    Problemstar selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Problemstar record, @Param("example") ProblemstarExample example);

    int updateByExample(@Param("record") Problemstar record, @Param("example") ProblemstarExample example);

    int updateByPrimaryKeySelective(Problemstar record);

    int updateByPrimaryKey(Problemstar record);
}