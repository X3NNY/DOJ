package pers.dreamer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dreamer.bean.Goods;
import pers.dreamer.bean.Goodsorder;
import pers.dreamer.bean.Message;
import pers.dreamer.bean.User;
import pers.dreamer.service.GoodsService;
import pers.dreamer.service.MessageService;
import pers.dreamer.service.UserService;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.*;

@CrossOrigin
@RestController
public class MallController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    DateFormat dateFormat;

    @GetMapping("/api/mall/all")
    public Map<String,Object> getAllGoods() {
        Map<String,Object> ret = new HashMap<>();
        List<Goods> goods = goodsService.findGoodsByStatus(0);
        List<Map<String,Object>> list = new ArrayList<>();
        for (Goods goods1:goods) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("gid",goods1.getGid());
            tmp.put("title",goods1.getName());
            tmp.put("price",goods1.getPrice());
            tmp.put("cnt",goods1.getCount());
            tmp.put("image",goods1.getImage());
            list.add(tmp);
        }
        ret.put("state",0);
        ret.put("goods",list);
        return ret;
    }

    @GetMapping("/api/mall/adminall")
    public List<Map<String,Object>> getAdminAllGoods(HttpSession session) {
        List<Map<String,Object>> ret = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            return ret;
        }
        List<Goods> goods = goodsService.findGoodsByStatus(-1);
        for (Goods goods1:goods) {
            Map<String,Object> tmp = new HashMap<>();
            tmp.put("gid",goods1.getGid());
            tmp.put("title",goods1.getName());
            tmp.put("price",goods1.getPrice());
            tmp.put("cnt",goods1.getCount());
            tmp.put("image",goods1.getImage());
            tmp.put("status",goods1.getStatus());
            ret.add(tmp);
        }
        return ret;
    }

    @GetMapping("/api/mall/info/{gid}")
    public Map<String,Object> getGoodsInfo(@PathVariable("gid")Integer gid) {
        Map<String,Object> ret = new HashMap<>();
        Goods goods = goodsService.findGoodsByGid(gid);
        if (goods == null) {
            ret.put("state",1);
        } else {
            ret.put("state",0);
            ret.put("title",goods.getName());
            ret.put("text",goods.getDesciption());
            ret.put("price",goods.getPrice());
            ret.put("cnt",goods.getCount());
            ret.put("image",goods.getImage());
        }
        return ret;
    }

    @GetMapping("/api/mall/pay/{gid}")
    public Map<String,Object> payGoods(@PathVariable("gid")Integer gid, HttpSession session) {
        Map<String,Object> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Goods goods = goodsService.findGoodsByGid(gid);
        if (user == null) {
            ret.put("state",2);
        } else if (goods == null || goods.getCount() <= 0) {
            ret.put("state",3);
        } else if (goods.getPrice() < 0 || user.getCoins() < goods.getPrice()) {
            ret.put("state",1);
        } else {
            ret.put("state",0);

            user.setCoins(user.getCoins()-goods.getPrice());
            userService.updateUserInfo(user);

            goods.setCount(goods.getCount()-1);
            goodsService.updateGoodsInfo(goods);

            Goodsorder goodsorder = new Goodsorder();
            goodsorder.setDate(new Date());
            goodsorder.setGid(gid);
            goodsorder.setUid(user.getUid());
            goodsService.insertGoodsorder(goodsorder);

            Message message = new Message();
            message.setTargetuid(-1);
            message.setSenduid(-3);
            message.setTitle(user.getUsername()+"的订单");
            message.setContent("用户"+user.getUsername()+"在"+ dateFormat.format(goodsorder.getDate())+"购买了商品"+goods.getName()+"(GID:"+goods.getGid()+"),请尽快处理");
            messageService.insertMessage(message);
        }
        return ret;
    }

    @PostMapping("/api/mall/addgoods")
    public Map<String,Integer> addGoods(@RequestBody Goods goods, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() < 6) {
            ret.put("state",1);
            return ret;
        }
        goods.setStatus(0);
        goodsService.insertGoods(goods);
        ret.put("state",0);
        return ret;
    }

    @PutMapping("/api/mall/edit/{gid}")
    public Map<String,Integer> editGoods(@RequestBody Goods newGoods,@PathVariable("gid")Integer gid, HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Goods goods = goodsService.findGoodsByGid(gid);
        if (goods == null || user == null || user.getRole() < 6) {
            ret.put("state",1);
            return ret;
        }
        newGoods.setGid(goods.getGid());
        goodsService.updateGoodsInfo(newGoods);
        ret.put("state",0);
        return ret;
    }

    @PutMapping("/app/mall/changestatus/{gid}")
    public Map<String,Integer> changeGoodsStatus(@PathVariable("gid")Integer gid,HttpSession session) {
        Map<String,Integer> ret = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Goods goods = goodsService.findGoodsByGid(gid);
        if (user == null || goods == null || user.getRole() < 6) {
            ret.put("state",1);
            return ret;
        }
        goods.setStatus(goods.getStatus()^1);
        goodsService.updateGoodsInfo(goods);
        ret.put("state",0);
        return ret;
    }
}
