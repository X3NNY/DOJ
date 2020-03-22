package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.oj.judge.pojo.Submitlist;
import pers.dreamer.oj.judge.pojo.SubmitlistExample;

public interface SubmitlistMapper {
    long countByExample(SubmitlistExample example);

    int deleteByExample(SubmitlistExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Submitlist record);

    int insertSelective(Submitlist record);

    List<Submitlist> selectByExample(SubmitlistExample example);

    Submitlist selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Submitlist record, @Param("example") SubmitlistExample example);

    int updateByExample(@Param("record") Submitlist record, @Param("example") SubmitlistExample example);

    int updateByPrimaryKeySelective(Submitlist record);

    int updateByPrimaryKey(Submitlist record);

    @Select("select sid,pid,lang from submitlist where sid=#{sid} limit 0,1")
    Submitlist findSubmitlistLangBySid(Integer sid);

    @Select("select sid from submitlist where uid=#{uid} and pid=#{pid} and result=0 order by sid limit 0,1")
    Integer isExistSubmitByUidAndPid(@Param("uid") Integer uid,@Param("pid") Integer pid);
}