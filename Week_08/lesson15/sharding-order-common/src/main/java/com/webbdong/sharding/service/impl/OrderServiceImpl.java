package com.webbdong.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.webbdong.sharding.entity.Order;
import com.webbdong.sharding.mapper.OrderMapper;
import com.webbdong.sharding.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author Webb Dong
 * @date 2021-03-13 3:35 PM
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
