package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Exlang;
import pers.dreamer.bean.ExlangExample;

public interface ExlangMapper {
    long countByExample(ExlangExample example);

    int deleteByExample(ExlangExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Exlang record);

    int insertSelective(Exlang record);

    List<Exlang> selectByExample(ExlangExample example);

    Exlang selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Exlang record, @Param("example") ExlangExample example);

    int updateByExample(@Param("record") Exlang record, @Param("example") ExlangExample example);

    int updateByPrimaryKeySelective(Exlang record);

    int updateByPrimaryKey(Exlang record);
}