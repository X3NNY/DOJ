package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Tags;
import pers.dreamer.bean.TagsExample;

public interface TagsMapper {
    long countByExample(TagsExample example);

    int deleteByExample(TagsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Tags record);

    int insertSelective(Tags record);

    List<Tags> selectByExample(TagsExample example);

    Tags selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Tags record, @Param("example") TagsExample example);

    int updateByExample(@Param("record") Tags record, @Param("example") TagsExample example);

    int updateByPrimaryKeySelective(Tags record);

    int updateByPrimaryKey(Tags record);

    @Select("select id from tags where name=#{tag} order by id limit 0,1")
    Integer findTidByName(String tag);

    @Select("select name from tags where id=#{id} order by id limit 0,1")
    String findNameByTid(Integer tid);

    @Select("select * from tags")
    List<Tags> findAll();
}