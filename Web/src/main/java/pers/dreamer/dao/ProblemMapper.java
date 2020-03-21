package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;
import pers.dreamer.bean.Problem;
import pers.dreamer.bean.ProblemExample;

public interface ProblemMapper {
    long countByExample(ProblemExample example);

    int deleteByExample(ProblemExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Problem record);

    int insertSelective(Problem record);

    List<Problem> selectByExample(ProblemExample example);

    Problem selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Problem record, @Param("example") ProblemExample example);

    int updateByExample(@Param("record") Problem record, @Param("example") ProblemExample example);

    int updateByPrimaryKeySelective(Problem record);

    int updateByPrimaryKey(Problem record);

    @Select("select uid from problem where pid=#{pid} limit 0,1")
    Integer findUidByPid(Integer pid);

    @Select("select pid from problem where pid=#{pid} and (status=0 or uid=#{uid}) limit 0,1")
    Integer isProblemCanBeViewed(@Param("pid") Integer pid,@Param("uid") Integer uid);

    @Select("select title from problem where pid=#{pid} limit 0,1")
    String findTitleByPid(Integer pid);

    @Select("select pid from problem where pid=#{pid} limit 0,1")
    Integer isExistByPid(Integer pid);
}