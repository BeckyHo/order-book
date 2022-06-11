package com.order.book.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * order bean
 *
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 18:10
 */
public class Order implements Serializable, Cloneable {

    private static final long serialVersionUID = -750885820191796009L;

    /**
     * 全局唯一，随着时间递增
     */
    public Long orderId;

    public String accountNo;

    public String direction;

    public BigDecimal orderPrice;

    public BigDecimal orderAmount;

    public Date orderTime;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    protected Object clone() {
        Order order = new Order();
        order.setOrderId(this.getOrderId());
        order.setAccountNo(this.getAccountNo());
        order.setDirection(this.getDirection());
        order.setOrderPrice(this.getOrderPrice());
        order.setOrderAmount(this.getOrderAmount());
        order.setOrderTime(this.getOrderTime());
        return order;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", orderPrice=" + orderPrice + ", orderAmount=" + orderAmount
            + ", direction=" + direction + ", accountNo=" + accountNo + '}' + "<br/>";
    }
}
