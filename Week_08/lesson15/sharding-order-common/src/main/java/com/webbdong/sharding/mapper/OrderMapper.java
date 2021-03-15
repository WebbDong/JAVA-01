package com.webbdong.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.webbdong.sharding.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表 Mapper 接口
 * @author Webb Dong
 * @since 2021-03-13
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
