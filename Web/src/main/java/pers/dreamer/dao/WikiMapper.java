package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Wiki;
import pers.dreamer.bean.WikiExample;
public interface WikiMapper {
    long countByExample(WikiExample example);

    int deleteByExample(WikiExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Wiki record);

    int insertSelective(Wiki record);

    List<Wiki> selectByExample(WikiExample example);

    Wiki selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Wiki record, @Param("example") WikiExample example);

    int updateByExample(@Param("record") Wiki record, @Param("example") WikiExample example);

    int updateByPrimaryKeySelective(Wiki record);

    int updateByPrimaryKey(Wiki record);
}