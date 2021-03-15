package com.webbdong.sharding.test;

import com.webbdong.sharding.Application;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.MessageFormat;

/**
 * 订单分表创建
 * @author Webb Dong
 * @date 2021-03-13 5:06 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ShardingOrderTableCreator {

    @Value("${spring.shardingsphere.datasource.orderds0.jdbc-url}")
    private String orderds0JdbcUrl;

    @Value("${spring.shardingsphere.datasource.orderds0.username}")
    private String orderds0Username;

    @Value("${spring.shardingsphere.datasource.orderds0.password}")
    private String orderds0Password;

    @Value("${spring.shardingsphere.datasource.orderds1.jdbc-url}")
    private String orderds1JdbcUrl;

    @Value("${spring.shardingsphere.datasource.orderds1.username}")
    private String orderds1Username;

    @Value("${spring.shardingsphere.datasource.orderds1.password}")
    private String orderds1Password;

    private final static String DROP_IF_EXISTS_ORDER_TABLE_SQL = "DROP TABLE IF EXISTS `t_order_{0}`;";

    private final static String CREATE_ORDER_TABLE_SQL = "CREATE TABLE `t_order_{0}` (" +
            "    `id` BIGINT(20) NOT NULL COMMENT ''订单id''," +
            "    `user_id` BIGINT(20) NOT NULL COMMENT ''用户id''," +
            "    `coupon_id` BIGINT(20) DEFAULT NULL COMMENT ''优惠券id''," +
            "    `order_no` VARCHAR(64) DEFAULT NULL COMMENT ''订单编号''," +
            "    `total_amount` DECIMAL(10,2) DEFAULT NULL COMMENT ''订单总金额''," +
            "    `pay_amount` DECIMAL(10,2) DEFAULT NULL COMMENT ''应付金额（实际支付金额）''," +
            "    `freight_amount` DECIMAL(10,2) DEFAULT NULL COMMENT ''运费金额''," +
            "    `promotion_amount` DECIMAL(10,2) DEFAULT NULL COMMENT ''促销优化金额（促销价、满减、阶梯价）''," +
            "    `integration_amount` DECIMAL(10,2) DEFAULT NULL COMMENT ''积分抵扣金额''," +
            "    `coupon_amount` DECIMAL(10,2) DEFAULT NULL COMMENT ''优惠券抵扣金额''," +
            "    `pay_type` INT(1) DEFAULT NULL COMMENT ''支付方式：0->未支付；1->支付宝；2->微信''," +
            "    `source_type` INT(1) DEFAULT NULL COMMENT ''订单来源：0->PC下单；1->app下单''," +
            "    `status` INT(1) DEFAULT NULL COMMENT ''订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单''," +
            "    `order_type` INT(1) DEFAULT NULL COMMENT ''订单类型：0->正常订单；1->秒杀订单''," +
            "    `delivery_company` VARCHAR(64) DEFAULT NULL COMMENT ''配送物流公司''," +
            "    `delivery_no` VARCHAR(64) DEFAULT NULL COMMENT ''物流单号''," +
            "    `auto_confirm_day` INT(11) DEFAULT NULL COMMENT ''自动确认时间（天）''," +
            "    `receiver_name` VARCHAR(100) NOT NULL COMMENT ''收货人姓名''," +
            "    `receiver_phone` VARCHAR(32) NOT NULL COMMENT ''收货人电话''," +
            "    `receiver_post_code` VARCHAR(32) DEFAULT NULL COMMENT ''收货人邮编''," +
            "    `receiver_province` VARCHAR(32) DEFAULT NULL COMMENT ''省份/直辖市''," +
            "    `receiver_city` VARCHAR(32) DEFAULT NULL COMMENT ''城市''," +
            "    `receiver_region` VARCHAR(32) DEFAULT NULL COMMENT ''区''," +
            "    `receiver_detail_address` VARCHAR(200) DEFAULT NULL COMMENT ''详细地址''," +
            "    `remark` VARCHAR(500) DEFAULT NULL COMMENT ''订单备注''," +
            "    `confirm_status` INT(1) DEFAULT NULL COMMENT ''确认收货状态：0->未确认；1->已确认''," +
            "    `payment_time` TIMESTAMP COMMENT ''支付时间''," +
            "    `delivery_time` TIMESTAMP COMMENT ''发货时间''," +
            "    `receive_time` TIMESTAMP COMMENT ''确认收货时间''," +
            "    `comment_time` TIMESTAMP COMMENT ''评价时间''," +
            "    `created_time` TIMESTAMP COMMENT ''创建时间''," +
            "    `updated_time` TIMESTAMP COMMENT ''修改时间''," +
            "    `del_status` int(1) NOT NULL DEFAULT '0' COMMENT ''删除状态：0->未删除；1->已删除''," +
            "    PRIMARY KEY (`id`)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT=''订单表{0}'';";

    @Test
    public void reCreateAllShardingTables() {
        createShardingOrderTables(orderds0JdbcUrl, orderds0Username, orderds0Password);
        createShardingOrderTables(orderds1JdbcUrl, orderds1Username, orderds1Password);
    }

    @SneakyThrows
    private void createShardingOrderTables(String jdbcUrl, String username, String password) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            for (int i = 0; i < 16; i++) {
                String dropSQL = MessageFormat.format(DROP_IF_EXISTS_ORDER_TABLE_SQL, i);
                try (PreparedStatement stmt = conn.prepareStatement(dropSQL)) {
                    stmt.execute();
                }

                String createSQL = MessageFormat.format(CREATE_ORDER_TABLE_SQL, i);
                System.out.println(createSQL);
                try (PreparedStatement stmt = conn.prepareStatement(createSQL)) {
                    stmt.execute();
                }
            }
        }
    }

}
