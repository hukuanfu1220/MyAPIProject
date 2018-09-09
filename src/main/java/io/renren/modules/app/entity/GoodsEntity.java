package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@TableName("tb_goods")
public class GoodsEntity implements Serializable {

    @TableId
    private Long goodsId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 介绍
     */
    private String intro;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer num;

    private Date createtime;

    private String modifytime;

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getModifytime() {
        return modifytime;
    }

    /**
     * @return goods_id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取商品名
     *
     * @return name - 商品名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名
     *
     * @param name 商品名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取介绍
     *
     * @return intro - 介绍
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置介绍
     *
     * @param intro 介绍
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }
}
