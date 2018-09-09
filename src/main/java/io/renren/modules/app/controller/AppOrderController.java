package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.UUIDBuild;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.entity.OrderEntity;
import io.renren.modules.app.service.GoodsService;
import io.renren.modules.app.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app")
@Api("订单接口")
public class AppOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;

    @PostMapping("addoneorder")
    public R addOneOrder(@RequestBody Map map){
        Integer userId = (Integer) map.get("userId");
        Integer goodId = (Integer)map.get("goodsId");
        Integer num = (Integer)map.get("num");
        String orderNum = DateUtils.getOrderIdByTime(userId);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newDate = sDateFormat.format(new Date());

        GoodsEntity goodsEntity = goodsService.selectById(goodId);
        if (goodsEntity != null){
            if (goodsEntity.getNum() != 0 && num < goodsEntity.getNum()){

                BigDecimal price =goodsEntity.getPrice();
                BigDecimal nums = new BigDecimal(num);

                BigDecimal totalPrice = price.multiply(nums);

                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setGoodsId(goodId);
                orderEntity.setUserId(userId);
                orderEntity.setOrderNum(orderNum);
                orderEntity.setOrderPrice(totalPrice);
                orderEntity.setOrderStatus(1);
                orderEntity.setCreatetime(newDate);
                orderEntity.setGoodsNum(num);
                orderEntity.setDeletestatus(0);

                GoodsEntity entity = new GoodsEntity();
                entity.setNum(goodsEntity.getNum() - num);
                entity.setModifytime(newDate);

                boolean goods = goodsService.update(entity,new EntityWrapper<GoodsEntity>().eq("goods_id",goodId));

                if (goods){

                    boolean status = orderService.insert(orderEntity);
                    if (status){
                        return R.ok();
                    }else {
                        return R.error(303,"订单创建失败");
                    }

                }else {
                    return R.error(302,"商品数量不足");
                }


            }else {
               return R.error(301,"该商品已售完");
            }
        }else {
            return R.error(300,"不存在该商品了");
        }
    }


    @GetMapping("canceOrder")
    public R canceOneOrder(@RequestParam String orderNum){

        OrderEntity orderEntity = orderService.selectOne(new EntityWrapper<OrderEntity>().eq("order_num",orderNum));

        if (orderEntity != null){
            GoodsEntity entity = goodsService.selectById(orderEntity.getGoodsId());

            if (entity != null){

                OrderEntity orderEntity1 = new OrderEntity();
                orderEntity1.setOrderStatus(4);
                orderEntity1.setModifytime(DateUtils.getCurrentString());

                boolean orderRefresh = orderService.update(orderEntity1,new EntityWrapper<OrderEntity>().eq("order_num",orderNum));

                if (orderRefresh){

                    GoodsEntity goodsEntity = new GoodsEntity();
                    goodsEntity.setModifytime(DateUtils.getCurrentString());
                    goodsEntity.setNum(orderEntity.getGoodsNum() + entity.getNum());

                    boolean refresh = goodsService.update(goodsEntity,new EntityWrapper<GoodsEntity>().eq("goods_id",orderEntity.getGoodsId()));

                    if (refresh){
                        return R.ok();
                    }else {
                        return R.error(303,"商品添加失败");
                    }


                }else {
                    return R.error(302,"订单取消失败");
                }


            }else {
                return R.error(301,"不存在该商品");
            }

        }else {
            return R.error(300,"不存在该订单");
        }

    }

    @GetMapping("deleteorder")
    public R deleteOneOrder(@RequestParam String orderNum){
        OrderEntity orderEntity = orderService.selectOne(new EntityWrapper<OrderEntity>().eq("order_num",orderNum));
        if (orderEntity != null){
            OrderEntity entity = new OrderEntity();
            entity.setModifytime(DateUtils.getCurrentString());
            entity.setDeletestatus(1);

            boolean status = orderService.update(entity,new EntityWrapper<OrderEntity>().eq("order_num",orderNum));
            if (status){
                return R.ok();
            }else {
                return R.error(300,"删除失败");
            }

        }else {
            return R.error(300,"不存在该订单");
        }
    }

    @PostMapping("getAllorderlist")
    public R getAllOrderList(@RequestBody Map map){
        Integer userId = (Integer)map.get("userId");
        Integer type = (Integer)map.get("type");
        Integer current = (Integer)map.get("current");


        Wrapper<OrderEntity> wrapper = new EntityWrapper<OrderEntity>();
        if(type==0){
            wrapper.eq("user_id",userId).eq("deleteStatus",0);
        }else{
            wrapper.eq("user_id",userId).eq("deleteStatus",0).eq("order_status",type);

        }



        Page<OrderEntity> page = new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        Page<OrderEntity> list = orderService.selectPage(page,wrapper);

        return  R.ok().put("data",list);






    }


}
