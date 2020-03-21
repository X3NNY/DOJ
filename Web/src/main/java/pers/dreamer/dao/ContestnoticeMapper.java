package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Contestnotice;
import pers.dreamer.bean.ContestnoticeExample;

public interface ContestnoticeMapper {
    long countByExample(ContestnoticeExample example);

    int deleteByExample(ContestnoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contestnotice record);

    int insertSelective(Contestnotice record);

    List<Contestnotice> selectByExample(ContestnoticeExample example);

    Contestnotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contestnotice record, @Param("example") ContestnoticeExample example);

    int updateByExample(@Param("record") Contestnotice record, @Param("example") ContestnoticeExample example);

    int updateByPrimaryKeySelective(Contestnotice record);

    int updateByPrimaryKey(Contestnotice record);
}