package com.webbdong.sharding.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.webbdong.sharding.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Webb Dong
 * @date 2021-03-15 3:23 PM
 */
public class OrderUtils {

    private OrderUtils() {}

    public static Order createOrder() {
        Order order = new Order();
        order.setUserId(IdWorker.getId());
        // 优惠券id
        order.setCouponId(IdWorker.getId());
        // 订单编号
        order.setOrderNo("170916032679263329");
        // 订单总金额
        order.setTotalAmount(BigDecimal.valueOf(20.0));
        // 应付金额
        order.setPayAmount(BigDecimal.valueOf(25.0));
        // 运费金额
        order.setFreightAmount(BigDecimal.valueOf(5));
        // 促销优化金额
        order.setPromotionAmount(BigDecimal.ZERO);
        // 积分抵扣金额
        order.setIntegrationAmount(BigDecimal.ZERO);
        // 优惠券抵扣金额
        order.setCouponAmount(BigDecimal.ZERO);
        // 支付方式
        order.setPayType(ThreadLocalRandom.current().nextInt(3));
        // 订单来源
        order.setSourceType(ThreadLocalRandom.current().nextInt(2));
        // 订单状态
        order.setStatus(3);
        // 订单类型
        order.setOrderType(ThreadLocalRandom.current().nextInt(2));
        // 配送物流公司
        order.setDeliveryCompany("UPS");
        // 物流单号
        order.setDeliveryNo("170916032679263331");
        // 自动确认时间
        order.setAutoConfirmDay(7);
        // 收货人姓名
        order.setReceiverName("Webb");
        // 收货人电话
        order.setReceiverPhone("13813838438");
        // 收货人邮编
        order.setReceiverPostCode("201700");
        // 省份/直辖市
        order.setReceiverProvince("上海");
        // 城市
        order.setReceiverCity("上海");
        // 区
        order.setReceiverRegion("青浦区");
        // 详细地址
        order.setReceiverDetailAddress("上海青浦区");
        // 订单备注
        order.setRemark("备注");
        // 确认收货状态
        order.setConfirmStatus(1);
        // 支付时间
        order.setPaymentTime(Date.from(LocalDateTime.of(2021, 2, 5, 11, 30, 35)
                .atZone(ZoneId.systemDefault()).toInstant()));
        // 发货时间
        order.setDeliveryTime(Date.from(LocalDateTime.of(2021, 2, 6, 11, 30, 35)
                .atZone(ZoneId.systemDefault()).toInstant()));
        // 确认收货时间
        order.setReceiveTime(Date.from(LocalDateTime.of(2021, 2, 7, 11, 30, 35)
                .atZone(ZoneId.systemDefault()).toInstant()));
        // 评价时间
        order.setCommentTime(Date.from(LocalDateTime.of(2021, 2, 8, 11, 30, 35)
                .atZone(ZoneId.systemDefault()).toInstant()));
        // 创建时间
        order.setCreatedTime(Date.from(LocalDateTime.of(2021, 3, 6, 14, 30, 0)
                .atZone(ZoneId.systemDefault()).toInstant()));
        // 修改时间
        order.setUpdatedTime(Date.from(LocalDateTime.of(2021, 3, 6, 14, 30, 0)
                .atZone(ZoneId.systemDefault()).toInstant()));
        // 删除状态
        order.setDelStatus(0);
        return order;
    }

}
