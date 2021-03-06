package lesson13;

import lombok.SneakyThrows;
import util.HikaricpUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
 * @author Webb Dong
 * @date 2021-03-06 12:39 AM
 */
public class InsertOneMillionOrderData {

    private static final int RECORD_COUNT = 1000000;

    /**
     * 订单表一共 32 个字段
     */
    private static final int ORDER_TABLE_COLUMN_COUNT = 32;

    private static void fillOrderParamList(List<Object> paramList, int i) {
        // 用户id
        paramList.add(170916032679263335L);
        // 优惠券id
        paramList.add(170916032679263341L);
        // 订单编号
        paramList.add("170916032679263329");
        // 订单总金额
        paramList.add(20.0 + i);
        // 应付金额
        paramList.add(20.0 + i + 5);
        // 运费金额
        paramList.add(5);
        // 促销优化金额
        paramList.add(0.0);
        // 积分抵扣金额
        paramList.add(0.0);
        // 优惠券抵扣金额
        paramList.add(0.0);
        // 支付方式
        paramList.add(ThreadLocalRandom.current().nextInt(3));
        // 订单来源
        paramList.add(ThreadLocalRandom.current().nextInt(2));
        // 订单状态
        paramList.add(3);
        // 订单类型
        paramList.add(ThreadLocalRandom.current().nextInt(2));
        // 配送物流公司
        paramList.add("UPS");
        // 物流单号
        paramList.add("170916032679263331");
        // 自动确认时间
        paramList.add(7);
        // 收货人姓名
        paramList.add("Webb");
        // 收货人电话
        paramList.add("13813838438");
        // 收货人邮编
        paramList.add("201700");
        // 省份/直辖市
        paramList.add("上海");
        // 城市
        paramList.add("上海");
        // 区
        paramList.add("青浦区");
        // 详细地址
        paramList.add("上海青浦区");
        // 订单备注
        paramList.add("备注");
        // 确认收货状态
        paramList.add(1);
        // 支付时间
        paramList.add("2021-02-05 11:30:35");
        // 发货时间
        paramList.add("2021-02-06 11:30:35");
        // 确认收货时间
        paramList.add("2021-02-07 11:30:35");
        // 评价时间
        paramList.add("2021-02-08 11:30:35");
        // 创建时间
        paramList.add("2021-03-06 14:30:00");
        // 修改时间
        paramList.add("2021-03-06 14:30:00");
        // 删除状态
        paramList.add(0);
    }

    /**
     * 1、使用多个 values 进行批量插入方式
     *
     * 插入策略: 每 10000 条一个批次，条数到 10000 时，提交并插入一批
     *      rewriteBatchedStatements 关闭情况: 耗时 30561 ms
     *      rewriteBatchedStatements 开启情况: 耗时 34773 ms
     *
     * 插入策略: 每 20000 条一个批次，条数到 20000 时，提交并插入一批
     *      rewriteBatchedStatements 关闭情况: 耗时 30844 ms
     *      rewriteBatchedStatements 开启情况: 耗时 35334 ms
     */
    @SneakyThrows
    private static void way1() {
        String insertSql = "INSERT INTO `test`.`t_order`(`user_id`, `coupon_id`, `order_no`, `total_amount`, `pay_amount`," +
                "`freight_amount`, `promotion_amount`, `integration_amount`," +
                "`coupon_amount`, `pay_type`, `source_type`, `status`," +
                "`order_type`, `delivery_company`, `delivery_no`," +
                "`auto_confirm_day`, `receiver_name`, `receiver_phone`," +
                "`receiver_post_code`, `receiver_province`, `receiver_city`," +
                "`receiver_region`, `receiver_detail_address`, `remark`," +
                "`confirm_status`, `payment_time`, `delivery_time`, `receive_time`," +
                "`comment_time`, `created_time`, `updated_time`, `del_status`) ";
        // INSERT 一个批次的条数
        final int INSERT_BATCH_COUNT = 20000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 1; i <= RECORD_COUNT; i++) {
            fillOrderParamList(paramList, i);
            if (i % INSERT_BATCH_COUNT == 0) {
                HikaricpUtils.INSTANCE.executeBatchInsertByValues(
                        insertSql, INSERT_BATCH_COUNT, ORDER_TABLE_COLUMN_COUNT, paramList);
                paramList.clear();
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 2、使用批量插入方式
     *
     * 插入策略: 每 10000 条一个批次，条数到 10000 时，提交并插入一批
     *      rewriteBatchedStatements 关闭情况: 速度过慢，5-8分钟只插入 10 万条左右数据
     *      rewriteBatchedStatements 开启情况: 耗时 34420 ms
     *
     * 插入策略: 每 20000 条一个批次，条数到 20000 时，提交并插入一批
     *      rewriteBatchedStatements 开启情况: 耗时 36422 ms
     */
    @SneakyThrows
    private static void way2() {
        String insertSql = "INSERT INTO `test`.`t_order`(`user_id`, `coupon_id`, `order_no`, `total_amount`, `pay_amount`," +
                "`freight_amount`, `promotion_amount`, `integration_amount`," +
                "`coupon_amount`, `pay_type`, `source_type`, `status`," +
                "`order_type`, `delivery_company`, `delivery_no`," +
                "`auto_confirm_day`, `receiver_name`, `receiver_phone`," +
                "`receiver_post_code`, `receiver_province`, `receiver_city`," +
                "`receiver_region`, `receiver_detail_address`, `remark`," +
                "`confirm_status`, `payment_time`, `delivery_time`, `receive_time`," +
                "`comment_time`, `created_time`, `updated_time`, `del_status`) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        // INSERT 一个批次的条数
        final int INSERT_BATCH_COUNT = 20000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        for (int i = 1; i <= RECORD_COUNT; i++) {
            fillOrderParamList(paramList, i);
            if (i % INSERT_BATCH_COUNT == 0) {
                HikaricpUtils.INSTANCE.executeBatch(
                        insertSql, ORDER_TABLE_COLUMN_COUNT, paramList, INSERT_BATCH_COUNT);
                paramList.clear();
            }
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 3、使用批量插入方式 + 手动控制事务
     *
     * 插入策略: 每 10000 条一个批次，条数到 10000 时，提交并插入一批
     *      rewriteBatchedStatements 开启情况: 耗时 29202 ms
     *
     * 插入策略: 每 20000 条一个批次，条数到 20000 时，提交并插入一批
     *      rewriteBatchedStatements 开启情况: 耗时 29798 ms
     */
    @SneakyThrows
    private static void way3() {
        String insertSql = "INSERT INTO `test`.`t_order`(`user_id`, `coupon_id`, `order_no`, `total_amount`, `pay_amount`," +
                "`freight_amount`, `promotion_amount`, `integration_amount`," +
                "`coupon_amount`, `pay_type`, `source_type`, `status`," +
                "`order_type`, `delivery_company`, `delivery_no`," +
                "`auto_confirm_day`, `receiver_name`, `receiver_phone`," +
                "`receiver_post_code`, `receiver_province`, `receiver_city`," +
                "`receiver_region`, `receiver_detail_address`, `remark`," +
                "`confirm_status`, `payment_time`, `delivery_time`, `receive_time`," +
                "`comment_time`, `created_time`, `updated_time`, `del_status`) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        final int INSERT_BATCH_COUNT = 20000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(insertSql);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchByManualTransaction(
                            insertSql, stmt, ORDER_TABLE_COLUMN_COUNT, paramList, INSERT_BATCH_COUNT);
                    paramList.clear();
                }
            }
            conn.commit();
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 4、使用多个 values 进行批量插入方式 + 手动控制事务 + 将 max_allowed_packet SQL 语句长度大小调整为 10M
     *
     * 插入策略: 每 50000 条一个批次，条数到 50000 时，提交并插入一批，耗时: 28741 ms
     *
     * 插入策略: 每 100000 条一个批次，条数到 100000 时，提交并插入一批，耗时: 31102 ms
     */
    @SneakyThrows
    private static void way4() {
        String insertSql = "INSERT INTO `test`.`t_order`(`user_id`, `coupon_id`, `order_no`, `total_amount`, `pay_amount`," +
                "`freight_amount`, `promotion_amount`, `integration_amount`," +
                "`coupon_amount`, `pay_type`, `source_type`, `status`," +
                "`order_type`, `delivery_company`, `delivery_no`," +
                "`auto_confirm_day`, `receiver_name`, `receiver_phone`," +
                "`receiver_post_code`, `receiver_province`, `receiver_city`," +
                "`receiver_region`, `receiver_detail_address`, `remark`," +
                "`confirm_status`, `payment_time`, `delivery_time`, `receive_time`," +
                "`comment_time`, `created_time`, `updated_time`, `del_status`) ";
        // INSERT 一个批次的条数
        final int INSERT_BATCH_COUNT = 100000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            String fullSql = HikaricpUtils.INSTANCE.generateFullInsertByValuesSql(insertSql, INSERT_BATCH_COUNT,
                    ORDER_TABLE_COLUMN_COUNT);
            PreparedStatement stmt = conn.prepareStatement(fullSql);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchInsertByValuesAndManualTransaction(stmt, paramList);
                    paramList.clear();
                }
            }
            conn.commit();
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    /**
     * 5、使用批量插入方式 + 手动控制事务 + 将 max_allowed_packet SQL 语句长度大小调整为 10M
     *
     * 插入策略: 每 50000 条一个批次，条数到 50000 时，提交并插入一批，耗时: 29961 ms
     */
    @SneakyThrows
    private static void way5() {
        String insertSql = "INSERT INTO `test`.`t_order`(`user_id`, `coupon_id`, `order_no`, `total_amount`, `pay_amount`," +
                "`freight_amount`, `promotion_amount`, `integration_amount`," +
                "`coupon_amount`, `pay_type`, `source_type`, `status`," +
                "`order_type`, `delivery_company`, `delivery_no`," +
                "`auto_confirm_day`, `receiver_name`, `receiver_phone`," +
                "`receiver_post_code`, `receiver_province`, `receiver_city`," +
                "`receiver_region`, `receiver_detail_address`, `remark`," +
                "`confirm_status`, `payment_time`, `delivery_time`, `receive_time`," +
                "`comment_time`, `created_time`, `updated_time`, `del_status`) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
        final int INSERT_BATCH_COUNT = 50000;
        List<Object> paramList = new ArrayList<>(INSERT_BATCH_COUNT * ORDER_TABLE_COLUMN_COUNT);
        long startTimeMillis = System.currentTimeMillis();
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(insertSql);
            for (int i = 1; i <= RECORD_COUNT; i++) {
                fillOrderParamList(paramList, i);
                if (i % INSERT_BATCH_COUNT == 0) {
                    HikaricpUtils.INSTANCE.executeBatchByManualTransaction(
                            insertSql, stmt, ORDER_TABLE_COLUMN_COUNT, paramList, INSERT_BATCH_COUNT);
                    paramList.clear();
                }
            }
            conn.commit();
        }
        long endTimeMillis = System.currentTimeMillis();
        System.out.println("耗时: " + (endTimeMillis - startTimeMillis));
    }

    public static void main(String[] args) {
//        way1();
//        way2();
//        way3();
//        way4();
        way5();
    }

}
