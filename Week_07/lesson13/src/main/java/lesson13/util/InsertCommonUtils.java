package lesson13.util;

import lombok.SneakyThrows;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Webb Dong
 * @date 2021-03-07 6:36 PM
 */
public class InsertCommonUtils {

    public static final String INSERT_INTO_SQL = "INSERT INTO `order`.`t_order`(" +
            "`user_id`, `coupon_id`, `order_no`, `total_amount`, `pay_amount`," +
            "`freight_amount`, `promotion_amount`, `integration_amount`," +
            "`coupon_amount`, `pay_type`, `source_type`, `status`," +
            "`order_type`, `delivery_company`, `delivery_no`," +
            "`auto_confirm_day`, `receiver_name`, `receiver_phone`," +
            "`receiver_post_code`, `receiver_province`, `receiver_city`," +
            "`receiver_region`, `receiver_detail_address`, `remark`," +
            "`confirm_status`, `payment_time`, `delivery_time`, `receive_time`," +
            "`comment_time`, `created_time`, `updated_time`, `del_status`) ";

    public static final String INSERT_INTO_VALUES_SQL = INSERT_INTO_SQL +
            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

    private static final SnowFlake snowFlake = new SnowFlake(3, 6);

    public static void fillOrderParamList(List<Object> paramList, int i) {
        // 用户id
        paramList.add(snowFlake.nextId());
        // 优惠券id
        paramList.add(snowFlake.nextId());
        // 订单编号
        paramList.add(String.valueOf(snowFlake.nextId()));
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

    @SneakyThrows
    public static void setStmt(PreparedStatement stmt, int j) {
        // 用户id
        stmt.setObject(1, snowFlake.nextId());
        // 优惠券id
        stmt.setObject(2, snowFlake.nextId());
        // 订单编号
        stmt.setObject(3, String.valueOf(snowFlake.nextId()));
        // 订单总金额
        stmt.setObject(4, 20.0 + j);
        // 应付金额
        stmt.setObject(5, 20.0 + j + 5);
        // 运费金额
        stmt.setObject(6, 5);
        // 促销优化金额
        stmt.setObject(7, 0.0);
        // 积分抵扣金额
        stmt.setObject(8, 0.0);
        // 优惠券抵扣金额
        stmt.setObject(9, 0.0);
        // 支付方式
        stmt.setObject(10, ThreadLocalRandom.current().nextInt(3));
        // 订单来源
        stmt.setObject(11, ThreadLocalRandom.current().nextInt(2));
        // 订单状态
        stmt.setObject(12, 3);
        // 订单类型
        stmt.setObject(13, ThreadLocalRandom.current().nextInt(2));
        // 配送物流公司
        stmt.setObject(14, "UPS");
        // 物流单号
        stmt.setObject(15, "170916032679263331");
        // 自动确认时间
        stmt.setObject(16, 7);
        // 收货人姓名
        stmt.setObject(17, "Webb");
        // 收货人电话
        stmt.setObject(18, "13813838438");
        // 收货人邮编
        stmt.setObject(19, "201700");
        // 省份/直辖市
        stmt.setObject(20, "上海");
        // 城市
        stmt.setObject(21, "上海");
        // 区
        stmt.setObject(22, "青浦区");
        // 详细地址
        stmt.setObject(23, "上海青浦区");
        // 订单备注
        stmt.setObject(24, "备注");
        // 确认收货状态
        stmt.setObject(25, 1);
        // 支付时间
        stmt.setObject(26, "2021-02-05 11:30:35");
        // 发货时间
        stmt.setObject(27, "2021-02-06 11:30:35");
        // 确认收货时间
        stmt.setObject(28, "2021-02-07 11:30:35");
        // 评价时间
        stmt.setObject(29, "2021-02-08 11:30:35");
        // 创建时间
        stmt.setObject(30, "2021-03-06 14:30:00");
        // 修改时间
        stmt.setObject(31, "2021-03-06 14:30:00");
        // 删除状态
        stmt.setObject(32, 0);
    }

}
