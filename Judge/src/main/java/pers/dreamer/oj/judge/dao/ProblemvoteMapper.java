package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.oj.judge.pojo.Problemvote;
import pers.dreamer.oj.judge.pojo.ProblemvoteExample;

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
}