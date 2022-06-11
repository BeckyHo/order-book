package com.order.book.order;

import com.order.book.utils.OrderBookUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author chunming.ygy@gmail.com
 * @date 2022/6/11 09:34
 */
@SpringBootTest
public class TradeEngineTest {

    @Resource
    private TradeEngine tradeEngine;

    class Task implements Runnable {

        CountDownLatch countDownLatch = null;
        Order order = null;

        public Task(CountDownLatch countDownLatch, Order order) {
            this.countDownLatch = countDownLatch;
            this.order = order;
        }

        @Override
        public void run() {
            tradeEngine.processOrder(order);
            countDownLatch.countDown();
        }
    }

    @Test
    void processOrder() {

        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            Thread t = new Thread(new Task(countDownLatch, generateOrder()));
            t.start();
        }

        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    @Test
    void cancel() {}

    public Order generateOrder() {
        int num = Math.abs(new Random().nextInt(100));
        Order order = new Order();
        order.setOrderId(OrderBookUtils.generateMarkerOrderId());
        order.setOrderTime(new Date());
        order.setAccountNo("Mk" + num);
        order.setDirection(num % 2 == 0 ? OrderBookUtils.BUY : OrderBookUtils.SELL);
        // 防止出现无效数据
        order.setOrderPrice(new BigDecimal(num == 0 ? 10 : num));
        order.setOrderAmount(new BigDecimal(num == 0 ? 10 : num));

        return order;
    }

}