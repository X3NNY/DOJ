package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pers.dreamer.bean.Blogvote;
import pers.dreamer.bean.BlogvoteExample;

public interface BlogvoteMapper {
    long countByExample(BlogvoteExample example);

    int deleteByExample(BlogvoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Blogvote record);

    int insertSelective(Blogvote record);

    List<Blogvote> selectByExample(BlogvoteExample example);

    Blogvote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Blogvote record, @Param("example") BlogvoteExample example);

    int updateByExample(@Param("record") Blogvote record, @Param("example") BlogvoteExample example);

    int updateByPrimaryKeySelective(Blogvote record);

    int updateByPrimaryKey(Blogvote record);

    @Select("select vote from blogvote where bid=#{bid} and uid=#{uid} order by id limit 0,1")
    Integer findVoteByBidAndUid(@Param("bid") Integer bid,@Param("uid") Integer uid);
}