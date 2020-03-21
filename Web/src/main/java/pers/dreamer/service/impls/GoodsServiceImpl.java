package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.Goods;
import pers.dreamer.bean.GoodsExample;
import pers.dreamer.bean.Goodsorder;
import pers.dreamer.dao.GoodsMapper;
import pers.dreamer.dao.GoodsorderMapper;
import pers.dreamer.service.GoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GoodsorderMapper goodsorderMapper;

    @Override
    public Goods findGoodsByGid(Integer gid) {
        return goodsMapper.selectByPrimaryKey(gid);
    }

    @Transactional
    @Override
    public void updateGoodsInfo(Goods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Transactional
    @Override
    public void insertGoodsorder(Goodsorder goodsorder) {
        goodsorderMapper.insertSelective(goodsorder);
    }

    @Override
    public List<Goods> findGoodsByStatus(Integer status) {
        GoodsExample example = new GoodsExample();
        if (status != null && status != -1) {
            example.createCriteria().andStatusEqualTo(status);
        }
        return goodsMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public void insertGoods(Goods goods) {
        goodsMapper.insertSelective(goods);
    }
}
