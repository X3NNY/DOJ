package pers.dreamer.service;

import pers.dreamer.bean.Goods;
import pers.dreamer.bean.Goodsorder;

import java.util.List;

public interface GoodsService {

    Goods findGoodsByGid(Integer gid);

    void updateGoodsInfo(Goods goods);

    void insertGoodsorder(Goodsorder goodsorder);

    List<Goods> findGoodsByStatus(Integer status);

    void insertGoods(Goods goods);
}
