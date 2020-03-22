package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.oj.judge.pojo.Submitcollection;
import pers.dreamer.oj.judge.pojo.SubmitcollectionExample;

public interface SubmitcollectionMapper {
    long countByExample(SubmitcollectionExample example);

    int deleteByExample(SubmitcollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Submitcollection record);

    int insertSelective(Submitcollection record);

    List<Submitcollection> selectByExample(SubmitcollectionExample example);

    Submitcollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Submitcollection record, @Param("example") SubmitcollectionExample example);

    int updateByExample(@Param("record") Submitcollection record, @Param("example") SubmitcollectionExample example);

    int updateByPrimaryKeySelective(Submitcollection record);

    int updateByPrimaryKey(Submitcollection record);
}