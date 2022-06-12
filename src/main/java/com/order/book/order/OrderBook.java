package com.order.book.order;

import com.order.book.utils.OrderBookUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 18:20
 */
public class OrderBook {

    public String direction;

    public TreeMap<OrderSortKey, Order> orderBookMap;

    public OrderBook(String direction) {
        this.direction = direction;
        this.orderBookMap = new TreeMap<>(StringUtils.pathEquals(direction, OrderBookUtils.BUY) ? SORT_BUY : SORT_SELL);
    }

    public Order getFirstOrder() {
        return this.orderBookMap.isEmpty() ? null : this.orderBookMap.firstEntry().getValue();
    }

    public boolean remove(Order order) {
        return this.orderBookMap.remove(new OrderSortKey(order.getOrderPrice(), order.getOrderId())) != null;
    }

    public Order getOrder(Long orderId, BigDecimal price) {
        return this.orderBookMap.get(new OrderSortKey(price, orderId));
    }

    public boolean addOrder(Order order) {
        return this.orderBookMap.put(new OrderSortKey(order.getOrderPrice(), order.getOrderId()), order) != null;
    }

    public int size() {
        return this.orderBookMap.size();
    }

    /**
     * 卖家订单匹配规则：
     * 1. 价格低优先；
     * 2. 时间早优先
     */
    private Comparator<OrderSortKey> SORT_SELL = new Comparator<OrderSortKey>() {
        @Override
        public int compare(OrderSortKey o1, OrderSortKey o2) {
            int rev = o1.getOrderPrice().compareTo(o2.getOrderPrice());
            return rev == 0 ? Long.compare(o1.getOrderId(), o2.getOrderId()) : rev;
        }
    };

    /**
     * 买家订单匹配规则：
     * 1. 价格高优先；
     * 2. 时间早优先
     */
    private Comparator<OrderSortKey> SORT_BUY = new Comparator<OrderSortKey>() {
        @Override
        public int compare(OrderSortKey o1, OrderSortKey o2) {
            int rev = o2.getOrderPrice().compareTo(o1.getOrderPrice());
            return rev == 0 ? Long.compare(o1.getOrderId(), o2.getOrderId()) : rev;
        }
    };

    @Override
    public String toString() {
        if (orderBookMap.isEmpty()) {
            return "empty";
        }

        List<String> orderList = new ArrayList<>(10);
        for (Map.Entry<OrderSortKey, Order> entry : this.orderBookMap.entrySet()) {
            orderList.add(entry.getValue().toString());
        }
        if (direction == OrderBookUtils.SELL) {
            Collections.reverse(orderList);
        }
        return String.join("\n", orderList);
    }
}
