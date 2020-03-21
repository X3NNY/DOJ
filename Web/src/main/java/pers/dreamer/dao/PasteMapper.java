package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Paste;
import pers.dreamer.bean.PasteExample;

public interface PasteMapper {
    long countByExample(PasteExample example);

    int deleteByExample(PasteExample example);

    int deleteByPrimaryKey(String id);

    int insert(Paste record);

    int insertSelective(Paste record);

    List<Paste> selectByExample(PasteExample example);

    Paste selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Paste record, @Param("example") PasteExample example);

    int updateByExample(@Param("record") Paste record, @Param("example") PasteExample example);

    int updateByPrimaryKeySelective(Paste record);

    int updateByPrimaryKey(Paste record);
}