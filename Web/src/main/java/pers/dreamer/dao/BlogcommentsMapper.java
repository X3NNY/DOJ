package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Blogcomments;
import pers.dreamer.bean.BlogcommentsExample;

public interface BlogcommentsMapper {
    long countByExample(BlogcommentsExample example);

    int deleteByExample(BlogcommentsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blogcomments record);

    int insertSelective(Blogcomments record);

    List<Blogcomments> selectByExample(BlogcommentsExample example);

    Blogcomments selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blogcomments record, @Param("example") BlogcommentsExample example);

    int updateByExample(@Param("record") Blogcomments record, @Param("example") BlogcommentsExample example);

    int updateByPrimaryKeySelective(Blogcomments record);

    int updateByPrimaryKey(Blogcomments record);
}