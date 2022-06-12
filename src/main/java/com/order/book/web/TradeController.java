package com.order.book.web;

import com.order.book.order.Order;
import com.order.book.order.TradeEngine;
import com.order.book.utils.OrderBookUtils;
import com.order.book.utils.ErrorCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/10 20:36
 */
@RestController
@RequestMapping("/trade")
public class TradeController {

    @Resource
    private TradeEngine tradeEngine;

    @RequestMapping(method = RequestMethod.GET, value = "/add-order")
    public String order(String accountNo, String direction, String price, String amount) {
        Order order = new Order();
        order.setOrderId(OrderBookUtils.generateMarkerOrderId());
        order.setOrderTime(new Date());
        order.setAccountNo(accountNo);
        order.setDirection(direction);
        BigDecimal orderPrice = new BigDecimal(price);
        BigDecimal orderAmount = new BigDecimal(amount);
        if (orderPrice.compareTo(BigDecimal.ZERO) <= 0 || orderAmount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("invalid param");
            return ErrorCode.INVALID_PARAM;
        }
        order.setOrderPrice(orderPrice);
        order.setOrderAmount(orderAmount);

        return tradeEngine.processOrder(order);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-buy-book")
    public String getBuyBook() {
        StringBuffer sb = new StringBuffer();
        sb.append("buy book info:").append("<br/>");
        sb.append(tradeEngine.getBuyerBook());

        return sb.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-sell-book")
    public String getSellBook() {
        StringBuffer sb = new StringBuffer();

        sb.append("sell book info:").append("<br/>");
        sb.append(tradeEngine.getSellBook());

        return sb.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get-latest-trade")
    public String getLatestTradeInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("latest transaction:").append("<br/>");
        tradeEngine.getMatchResult().stream().forEach(e -> {
            sb.append("tradeNo: " + e.getTradeNo() + ", price: " + e.getPrice() + ", amount: " + e.getAmount())
                .append("<br/>");
        });
        return sb.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cancel-order")
    public String cancelOrder(String direction, Long orderId, String price) {
        Order order = new Order();
        order.setDirection(direction);

        BigDecimal orderPrice = new BigDecimal(price);
        if (orderId == null || orderPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return ErrorCode.INVALID_PARAM;
        }

        order.setOrderId(orderId);
        order.setOrderPrice(orderPrice);
        return tradeEngine.cancel(order) ? ErrorCode.SUCCESS : ErrorCode.FAIL;
    }

}
