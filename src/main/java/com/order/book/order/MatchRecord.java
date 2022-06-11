package com.order.book.order;

import java.math.BigDecimal;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 18:39
 */
public class MatchRecord {

    private String tradeNo;
    private BigDecimal price;
    private BigDecimal amount;
    private Order makerOrder;
    private Order takerOrder;

    public MatchRecord(String tradeNo, BigDecimal price, BigDecimal amount, Order makerOrder, Order takerOrder) {
        this.tradeNo = tradeNo;
        this.price = price;
        this.amount = amount;
        this.makerOrder = makerOrder;
        this.takerOrder = takerOrder;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Order getMakerOrder() {
        return makerOrder;
    }

    public void setMakerOrder(Order makerOrder) {
        this.makerOrder = makerOrder;
    }

    public Order getTakerOrder() {
        return takerOrder;
    }

    public void setTakerOrder(Order takerOrder) {
        this.takerOrder = takerOrder;
    }

    @Override
    public String toString() {
        return "MatchRecord{" + "tradeNo='" + tradeNo + '\'' + ", price=" + price + ", amount=" + amount
            + ", makerOrder=" + makerOrder + ", takerOrder=" + takerOrder + '}';
    }
}
