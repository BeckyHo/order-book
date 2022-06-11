package com.order.book.order;

import com.order.book.utils.OrderBookUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 18:42
 */
public class MatchResult {

    public Order takerOrder;
    public List<MatchRecord> matchRecordList = new ArrayList<>();

    public MatchResult(Order takerOrder) {
        this.takerOrder = (Order)takerOrder.clone();
    }

    public synchronized void add(BigDecimal price, BigDecimal matchedAmount, Order makerOrder) {
        String tradeNo = OrderBookUtils.generateDealedOrderId();
        matchRecordList.add(new MatchRecord(tradeNo, price, matchedAmount, (Order)makerOrder.clone(), takerOrder));
    }

    public Order getTakerOrder() {
        return takerOrder;
    }

    public void setTakerOrder(Order takerOrder) {
        this.takerOrder = takerOrder;
    }

    public List<MatchRecord> getMatchRecordList() {
        return matchRecordList;
    }

    public void setMatchRecordList(List<MatchRecord> matchRecordList) {
        this.matchRecordList = matchRecordList;
    }

    @Override
    public String toString() {
        return "MatchResult{" + "takerOrder=" + takerOrder + ", matchRecordList=" + matchRecordList + '}';
    }
}
