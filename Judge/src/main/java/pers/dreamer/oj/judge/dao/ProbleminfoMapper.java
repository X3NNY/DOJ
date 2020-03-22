package pers.dreamer.oj.judge.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.oj.judge.pojo.Probleminfo;
import pers.dreamer.oj.judge.pojo.ProbleminfoExample;

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
}