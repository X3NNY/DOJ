package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Submitcollection;
import pers.dreamer.bean.SubmitcollectionExample;

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

    @Select("select id from submitcollection where sid=#{sid} and uid=#{uid} limit 0,1")
    Integer isCollectByUid(@Param("sid") Integer sid,@Param("uid") Integer uid);
}