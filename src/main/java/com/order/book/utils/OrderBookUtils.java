package com.order.book.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 20:51
 */
public class OrderBookUtils {

    /**
     * buy direction
     */
    public static final String BUY = "1000";

    public static final String BUY_DESC = "B";

    /**
     * sell direction
     */
    public static final String SELL = "1001";

    public static final String SELL_DESC = "S";

    // return direction description
    public static String getDirectionName(String direction) {
        switch (direction) {
            case BUY:
                return BUY_DESC;
            case SELL:
                return SELL_DESC;
            default:
                return "";
        }
    }

    // 用户下单时的订单id生成器
    public static final AtomicLong MAKER_ORDER_ID = new AtomicLong(0);

    // 成交时的id生成器
    public static final AtomicLong DEALED_ORDER_ID = new AtomicLong(0);

    // 用户下单时的订单id生成方法
    public static Long generateMarkerOrderId() {
        return MAKER_ORDER_ID.getAndIncrement();
    }

    public static String generateDealedOrderId() {
        return "dealedNo" + DEALED_ORDER_ID.getAndIncrement();
    }

}
