package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.oj.judge.pojo.Code;
import pers.dreamer.oj.judge.pojo.CodeExample;

public interface CodeMapper {
    long countByExample(CodeExample example);

    int deleteByExample(CodeExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Code record);

    int insertSelective(Code record);

    List<Code> selectByExample(CodeExample example);

    Code selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Code record, @Param("example") CodeExample example);

    int updateByExample(@Param("record") Code record, @Param("example") CodeExample example);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);
}