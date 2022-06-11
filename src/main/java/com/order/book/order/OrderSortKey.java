package com.order.book.order;

import java.math.BigDecimal;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 19:07
 */
public class OrderSortKey {

    private BigDecimal orderPrice;

    /**
     * 全局唯一，随着时间递增
     * @see Order orderId属性
     */
    private Long orderId;

    public OrderSortKey(BigDecimal orderPrice, Long orderId) {
        this.orderPrice = orderPrice;
        this.orderId = orderId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderSortKey{" +
                "orderPrice=" + orderPrice +
                ", orderId=" + orderId +
                '}';
    }
}
