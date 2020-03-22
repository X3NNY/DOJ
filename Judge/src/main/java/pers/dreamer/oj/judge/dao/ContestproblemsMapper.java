package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.oj.judge.pojo.Contestproblems;
import pers.dreamer.oj.judge.pojo.ContestproblemsExample;

public interface ContestproblemsMapper {
    long countByExample(ContestproblemsExample example);

    int deleteByExample(ContestproblemsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contestproblems record);

    int insertSelective(Contestproblems record);

    List<Contestproblems> selectByExample(ContestproblemsExample example);

    Contestproblems selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contestproblems record, @Param("example") ContestproblemsExample example);

    int updateByExample(@Param("record") Contestproblems record, @Param("example") ContestproblemsExample example);

    int updateByPrimaryKeySelective(Contestproblems record);

    int updateByPrimaryKey(Contestproblems record);

    @Select("update contestproblems set ac_cnt=ac_cnt+${ac},all_cnt=all_cnt+1 where cid=#{cid} and pid=#{pid} limit 1")
    void updateByCidAndPid(@Param("cid") Integer cid,@Param("cid") Integer pid,@Param("ac") Integer ac);
}