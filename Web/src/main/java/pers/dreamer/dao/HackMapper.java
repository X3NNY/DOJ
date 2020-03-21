package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Hack;
import pers.dreamer.bean.HackExample;

public interface HackMapper {
    long countByExample(HackExample example);

    int deleteByExample(HackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hack record);

    int insertSelective(Hack record);

    List<Hack> selectByExample(HackExample example);

    Hack selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hack record, @Param("example") HackExample example);

    int updateByExample(@Param("record") Hack record, @Param("example") HackExample example);

    int updateByPrimaryKeySelective(Hack record);

    int updateByPrimaryKey(Hack record);
}