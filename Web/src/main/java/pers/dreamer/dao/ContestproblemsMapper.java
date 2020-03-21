package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Contestproblems;
import pers.dreamer.bean.ContestproblemsExample;
import pers.dreamer.bean.Problem;

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

    @Select("select pid from contestproblems where cid=#{cid} and num=#{num} limit 0,1")
    Integer findPidByCidAndNum(@Param("cid") Integer cid,@Param("num") Integer num);

    @Select("select count(id) from contestproblems where cid=#{cid}")
    Integer countProblemsByCid(Integer cid);

    @Select("select p.pid,p.title from contestproblems c inner join problem p on c.pid = p.pid where cid=#{cid}")
    List<Problem> findProblemTitleByCid(Integer cid);

    @Select("select ac_cnt from contestproblems where cid=#{cid} and pid=#{pid} order by id limit 0,1")
    Integer findAcCntByCidAndPid(@Param("cid") Integer cid,@Param("pid") Integer pid);

    @Select("select all_cnt from contestproblems where cid=#{cid} and pid=#{pid} order by id limit 0,1")
    Integer findAllCntByCidAndPid(@Param("cid") Integer cid,@Param("pid") Integer pid);

    @Select("select id,pid,num from contestproblems where cid=#{cid} order by id")
    List<Contestproblems> findProblemsByCid(Integer cid);

    @Select("update contestproblems set ac_cnt=ac_cnt+${ac},all_cnt=all_cnt+1 where cid=#{cid} and pid=#{pid} limit 1")
    void updateByCidAndPid(@Param("cid") Integer cid,@Param("pid") Integer pid,@Param("ac") Integer ac);
}