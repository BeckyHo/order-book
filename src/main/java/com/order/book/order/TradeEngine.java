package com.order.book.order;

import com.order.book.utils.OrderBookUtils;
import com.order.book.utils.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 19:18
 */
@Service("tradeEngine")
public class TradeEngine {

    public OrderBook buyBook = new OrderBook(OrderBookUtils.BUY);
    public OrderBook sellBook = new OrderBook(OrderBookUtils.SELL);
    public List<MatchRecord> matchResultList = new ArrayList<>();
    // 当前匹配市场价格
    public BigDecimal marketPrice = BigDecimal.ZERO;

    /**
     * 订单匹配入口
     * @param order
     * @return
     */
    public String processOrder(Order order) {
        MatchResult rev = null;
        switch (order.getDirection()) {
            case OrderBookUtils.BUY:
                rev = processOrder(order, this.sellBook, this.buyBook);
                break;
            case OrderBookUtils.SELL:
                rev = processOrder(order, this.buyBook, this.sellBook);
                break;
            default:
                return ErrorCode.FAIL;
        }

        matchResultList.addAll(rev.getMatchRecordList());
        return ErrorCode.SUCCESS;
    }

    /**
     * 订单匹配逻辑处理
     * @param takerOrder 当前订单
     * @param makerBook 对手订单薄
     * @param anotherBook 本方订单薄
     * @return
     */
    private synchronized MatchResult processOrder(Order takerOrder, OrderBook makerBook, OrderBook anotherBook) {
        MatchResult matchResult = new MatchResult(takerOrder);
        while (true) {
            Order makerOrder = makerBook.getFirstOrder();
            // 对手放无订单
            if (makerOrder == null) {
                break;
            }

            // 买/卖 基本逻辑判断，检查价格是否有交集
            if (StringUtils.pathEquals(takerOrder.direction, OrderBookUtils.BUY)
                && takerOrder.getOrderPrice().compareTo(makerOrder.getOrderPrice()) < 0) {
                break;
            } else if (StringUtils.pathEquals(takerOrder.direction, OrderBookUtils.SELL)
                && takerOrder.getOrderPrice().compareTo(makerOrder.getOrderPrice()) > 0) {
                break;
            }

            // 记录成交价格
            this.marketPrice = makerOrder.getOrderPrice();
            // 成交数量
            BigDecimal matchedAmount = takerOrder.getOrderAmount().min(makerOrder.getOrderAmount());
            // 保存成交订单数据快照
            matchResult.add(makerOrder.getOrderPrice(), matchedAmount, makerOrder);
            // log info
            System.out
                .println("A," + takerOrder.getOrderId() + "," + OrderBookUtils.getDirectionName(takerOrder.getDirection())
                    + "," + matchedAmount + "," + makerOrder.getOrderPrice());

            // 订单成交后逻辑处理
            takerOrder.setOrderAmount(takerOrder.getOrderAmount().subtract(matchedAmount));
            makerOrder.setOrderAmount(makerOrder.getOrderAmount().subtract(matchedAmount));
            if (makerOrder.getOrderAmount().signum() == 0) {
                makerBook.remove(makerOrder);
            }
            if (takerOrder.getOrderAmount().signum() == 0) {
                break;
            }
        }

        // 未完全成交的订单，保存到订单薄中
        if (takerOrder.getOrderAmount().signum() > 0) {
            anotherBook.addOrder(takerOrder);
        }
        return matchResult;
    }

    public boolean cancel(Order order) {
        OrderBook book = StringUtils.pathEquals(order.direction, OrderBookUtils.BUY) ? this.buyBook : this.sellBook;
        // query order info
        Order cancelOrder = book.getOrder(order.getOrderId(), order.getOrderPrice());
        boolean rev = false;
        if (cancelOrder != null) {
            rev = book.remove(order);
        }

        if (cancelOrder != null && rev) {
            System.out
                .println("X," + cancelOrder.getOrderId() + "," + OrderBookUtils.getDirectionName(cancelOrder.getDirection())
                    + "," + cancelOrder.getOrderAmount() + "," + cancelOrder.getOrderPrice());
        }

        return rev;
    }

    public String getBuyerBook() {
        return buyBook.toString();
    }

    public String getSellBook() {
        return sellBook.toString();
    }

    public List<MatchRecord> getMatchResult() {
        return matchResultList;
    }

}
