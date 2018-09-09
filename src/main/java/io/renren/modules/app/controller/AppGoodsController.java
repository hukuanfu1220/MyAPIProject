package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.R;
import io.renren.modules.app.entity.GoodsEntity;
import io.renren.modules.app.service.GoodsService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/app")
@Api("APP商品接口")
public class AppGoodsController {

    @Autowired
    private GoodsService goodsService;


    @PostMapping("addgoods")
    public R addGoods(@RequestBody Map map) {

        String name = (String) map.get("name");
        String intro = (String) map.get("intro");
        String prices = (String) map.get("price");
        BigDecimal price = new BigDecimal(prices);
        Integer num = (Integer) map.get("num");

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newDate = sDateFormat.format(new Date());

        GoodsEntity entity = goodsService.selectOne(new EntityWrapper<GoodsEntity>().eq("name", name));

        if (entity != null){

            Long goodid = entity.getGoodsId();
            Integer oldnum = entity.getNum();
            BigDecimal oldprice = entity.getPrice();

            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setName(name);
            goodsEntity.setNum(num + oldnum);

            if (price.equals("") ){
                goodsEntity.setPrice(oldprice);
            }else {
                goodsEntity.setPrice(price);
            }
            goodsEntity.setModifytime(newDate);

            boolean status = goodsService.update(goodsEntity,new EntityWrapper<GoodsEntity>().eq("goods_id",goodid));

            if (status){
                return R.ok();
            }else {
                return R.error(300,"添加失败");
            }
        }else {

            GoodsEntity goodsEntity = new GoodsEntity();
            goodsEntity.setName(name);
            goodsEntity.setNum(num);
            goodsEntity.setIntro(intro);
            if (prices == null){
                return R.error(301,"价格不能为空");
            }else {
                goodsEntity.setPrice(price);
            }

            boolean status = goodsService.insertAllColumn(goodsEntity);

            if (status){
                return R.ok();
            }else {
                return R.error(300,"添加失败");
            }
        }
    }



    @PostMapping("modifygoods")
    public R modifyGoods(@RequestBody Map map){
        Long goodsId = Long.parseLong(map.get("goodsId").toString());
        String name = (String) map.get("name");
        String intro = (String)map.get("intro");
        Integer num = (Integer)map.get("num");
        BigDecimal price =new BigDecimal(map.get("price").toString()) ;

        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newDate=sDateFormat.format(new Date());


        GoodsEntity status = goodsService.selectOne(new EntityWrapper<GoodsEntity>().eq("goods_id",goodsId));


        if (status != null){


            GoodsEntity goodsEntitys = new GoodsEntity();
            if (num != null){
                goodsEntitys.setName(name);
            }

            if (intro != null){
                goodsEntitys.setIntro(intro);
            }

            if (num != 0){
                goodsEntitys.setNum(num + status.getNum());
            }

            if (price.toString() != null){
                goodsEntitys.setPrice(price);
            }
            goodsEntitys.setModifytime(newDate);

            boolean entity = goodsService.update(goodsEntitys,new EntityWrapper<GoodsEntity>().eq("goods_id",goodsId));


            if (entity){
                return R.ok();
            }else {
                return R.error(300,"修改失败");
            }

        }else {

            return R.error(300,"不存在该商品");

        }

    }





}
