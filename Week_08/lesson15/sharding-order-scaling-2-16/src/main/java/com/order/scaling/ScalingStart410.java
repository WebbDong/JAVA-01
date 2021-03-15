package com.order.scaling;

import com.webbdong.sharding.ScalingUtils;
import com.webbdong.sharding.model.scaling4_1_0.DataSourceParameter;
import lombok.SneakyThrows;

import java.io.InputStream;

/**
 * shardingsphere-4.1.0-sharding-scaling
 * @author Webb Dong
 * @date 2021-03-15 12:27 AM
 */
public class ScalingStart410 {

    @SneakyThrows
    public static void main(String[] args) {
        InputStream in = ScalingStart410.class.getClassLoader()
                .getResourceAsStream("ScalingSourceConfig-4.1.0.yml");
        ScalingUtils.startScaling(
                "http://localhost:8888/shardingscaling/job/start",
                in,
                DataSourceParameter.builder()
                        .name("order_sharding_db")
                        .url("jdbc:mysql://127.0.0.1:3307/order_sharding_db?serverTimezone=UTC&useSSL=false")
                        .username("root")
                        .password("123456")
                        .build());
    }

}
