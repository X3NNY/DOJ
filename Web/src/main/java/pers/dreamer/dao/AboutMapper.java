package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.About;
import pers.dreamer.bean.AboutExample;

public interface AboutMapper {
    long countByExample(AboutExample example);

    int deleteByExample(AboutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(About record);

    int insertSelective(About record);

    List<About> selectByExample(AboutExample example);

    About selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") About record, @Param("example") AboutExample example);

    int updateByExample(@Param("record") About record, @Param("example") AboutExample example);

    int updateByPrimaryKeySelective(About record);

    int updateByPrimaryKey(About record);
}