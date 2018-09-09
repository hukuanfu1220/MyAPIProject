package io.renren.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("tb_orders")
public class OrderEntity implements Serializable {

    @TableId
    private Long orderId;

    private String orderNum;

    private Integer goodsId;

    private Integer userId;

    private BigDecimal orderPrice;

    private Integer orderStatus;

    /**
     * 数量
     */
    private Integer goodsNum;

    private String createtime;

    private String modifytime;

    private Integer deletestatus;

    public void setDeletestatus(Integer deletestatus) {
        this.deletestatus = deletestatus;
    }

    public Integer getDeletestatus() {
        return deletestatus;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getModifytime() {
        return modifytime;
    }

    /**
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return order_num
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * @return goods_id
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return order_price
     */
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    /**
     * @param orderPrice
     */
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    /**
     * @return order_status
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取数量
     *
     * @return goods_num - 数量
     */
    public Integer getGoodsNum() {
        return goodsNum;
    }

    /**
     * 设置数量
     *
     * @param goodsNum 数量
     */
    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

}
