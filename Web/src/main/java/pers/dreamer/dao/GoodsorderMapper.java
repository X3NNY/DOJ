package pers.dreamer.dao;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pers.dreamer.bean.Goodsorder;
import pers.dreamer.bean.GoodsorderExample;

public interface GoodsorderMapper {
    long countByExample(GoodsorderExample example);

    int deleteByExample(GoodsorderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goodsorder record);

    int insertSelective(Goodsorder record);

    List<Goodsorder> selectByExample(GoodsorderExample example);

    Goodsorder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goodsorder record, @Param("example") GoodsorderExample example);

    int updateByExample(@Param("record") Goodsorder record, @Param("example") GoodsorderExample example);

    int updateByPrimaryKeySelective(Goodsorder record);

    int updateByPrimaryKey(Goodsorder record);
}