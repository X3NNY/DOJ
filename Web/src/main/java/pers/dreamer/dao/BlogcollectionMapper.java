package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Blogcollection;
import pers.dreamer.bean.BlogcollectionExample;

public interface BlogcollectionMapper {
    long countByExample(BlogcollectionExample example);

    int deleteByExample(BlogcollectionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blogcollection record);

    int insertSelective(Blogcollection record);

    List<Blogcollection> selectByExample(BlogcollectionExample example);

    Blogcollection selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blogcollection record, @Param("example") BlogcollectionExample example);

    int updateByExample(@Param("record") Blogcollection record, @Param("example") BlogcollectionExample example);

    int updateByPrimaryKeySelective(Blogcollection record);

    int updateByPrimaryKey(Blogcollection record);

    @Select("select bid from blogcollection where uid=#{uid} order by id")
    List<Integer> findBidByUid(Integer uid);
}