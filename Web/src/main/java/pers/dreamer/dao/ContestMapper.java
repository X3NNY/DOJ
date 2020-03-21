package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Contest;
import pers.dreamer.bean.ContestExample;

public interface ContestMapper {
    long countByExample(ContestExample example);

    int deleteByExample(ContestExample example);

    int deleteByPrimaryKey(Integer cid);

    int insert(Contest record);

    int insertSelective(Contest record);

    List<Contest> selectByExample(ContestExample example);

    Contest selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") Contest record, @Param("example") ContestExample example);

    int updateByExample(@Param("record") Contest record, @Param("example") ContestExample example);

    int updateByPrimaryKeySelective(Contest record);

    int updateByPrimaryKey(Contest record);

    Contest findContestByNow();

    Integer findUidByCid(Integer cid);

    @Select("select cid from contest where cid=#{cid} and endstime<=now() order by cid limit 0,1")
    Integer isContestEnds(Integer cid);

    @Select("select cid,title,starttime from contest where cid=#{cid} order by cid limit 0,1")
    Contest findTitleAndStimeByCid(Integer cid);

    @Select("select cid,starttime,type from contest where cid=#{cid} order by cid limit 0,1")
    Contest findTimeAndTypeByCid(Integer cid);

    @Select("select type from contest where cid=#{cid} order by cid limit 0,1")
    Integer findTypeByCid(Integer cid);
}