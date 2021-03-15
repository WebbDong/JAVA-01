package com.webbdong.sharding.test;

import com.webbdong.sharding.Application;
import com.webbdong.sharding.entity.Order;
import com.webbdong.sharding.service.OrderService;
import com.webbdong.sharding.util.OrderUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Webb Dong
 * @date 2021-03-13 3:36 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testInsert() {
        Order order = OrderUtils.createOrder();
        orderService.save(order);
        System.out.println("order_id = " + order.getId());
        System.out.println("orderds" + (order.getUserId() % 4) + ".t_order_" + (order.getId() % 64));
    }

    @Test
    public void testSelectById() {
        Order order = orderService.getById(1370720362545889281L);
        System.out.println(order);
    }

}
