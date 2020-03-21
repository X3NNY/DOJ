package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import pers.dreamer.bean.Contestvote;
import pers.dreamer.bean.ContestvoteExample;

public interface ContestvoteMapper {
    long countByExample(ContestvoteExample example);

    int deleteByExample(ContestvoteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Contestvote record);

    int insertSelective(Contestvote record);

    List<Contestvote> selectByExample(ContestvoteExample example);

    Contestvote selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Contestvote record, @Param("example") ContestvoteExample example);

    int updateByExample(@Param("record") Contestvote record, @Param("example") ContestvoteExample example);

    int updateByPrimaryKeySelective(Contestvote record);

    int updateByPrimaryKey(Contestvote record);
}