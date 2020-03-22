package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.oj.judge.pojo.Contest;
import pers.dreamer.oj.judge.pojo.ContestExample;

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

    @Select("select type from contest wherer cid=#{cid} order by cid limit 0,1")
    Integer findTypeByCid(Integer cid);
}