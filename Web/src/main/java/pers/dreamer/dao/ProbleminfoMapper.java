package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import pers.dreamer.bean.Probleminfo;
import pers.dreamer.bean.ProbleminfoExample;

public interface ProbleminfoMapper {
    long countByExample(ProbleminfoExample example);

    int deleteByExample(ProbleminfoExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Probleminfo record);

    int insertSelective(Probleminfo record);

    List<Probleminfo> selectByExample(ProbleminfoExample example);

    Probleminfo selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Probleminfo record, @Param("example") ProbleminfoExample example);

    int updateByExample(@Param("record") Probleminfo record, @Param("example") ProbleminfoExample example);

    int updateByPrimaryKeySelective(Probleminfo record);

    int updateByPrimaryKey(Probleminfo record);

    @Select("update probleminfo set up_vote=up_vote+1 where pid=#{pid}")
    void upVote(Integer pid);

    @Select("update probleminfo set down_vote=down_vote+1 where pid=#{pid}")
    void downVote(Integer pid);
}