package lesson10.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC 事务，PrepareStatement，批处理
 * @author Webb Dong
 * @date 2021-02-24 15:00
 */
public class JdbcPrepareStatementTransaction {

    public static void main(String[] args) throws SQLException {
//        messageFormat();
//        insert();
//        batchInsert();
//        selectById(3L);
//        transactionalInsert();
        transactionalSavepoint();
    }

    /**
     * 事务保存点
     */
    private static void transactionalSavepoint() throws SQLException {
        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false);

            String insertSql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`) " +
                "VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, "Bird");
                stmt.setInt(2, 31);
                stmt.setString(3, "Bird");
                stmt.setTimestamp(4, Timestamp.valueOf("2021-02-26 18:45:15"));
                stmt.setTimestamp(5, Timestamp.valueOf("2021-02-26 18:45:15"));
                stmt.executeUpdate();
            }
            Savepoint birdInsertSavepoint = conn.setSavepoint("BirdInsert");

            insertSql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`, `id`) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, "Chris");
                stmt.setInt(2, 25);
                stmt.setString(3, "Chris");
                stmt.setTimestamp(4, Timestamp.valueOf("2021-02-26 19:45:15"));
                stmt.setTimestamp(5, Timestamp.valueOf("2021-02-26 19:45:15"));
                stmt.setLong(6, 1L);
                stmt.executeUpdate();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                // 全部回滚
//                conn.rollback();

                // 回滚到保存点并提交事务，Bird 用户插入成功，Chris 用户回滚
                conn.rollback(birdInsertSavepoint);
                conn.commit();
            }
        }
    }

    /**
     * 事务
     */
    private static int transactionalInsert() throws SQLException {
        String sql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`, `id`) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
//        String sql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`) " +
//                "VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection()) {
            // 禁用自动事务提交，开启手动事务控制
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "Curry");
                stmt.setInt(2, 31);
                stmt.setString(3, "Curry");
                stmt.setTimestamp(4, Timestamp.valueOf("2021-02-25 18:45:15"));
                stmt.setTimestamp(5, Timestamp.valueOf("2021-02-25 18:45:15"));
                stmt.setLong(6, 1L);
                int row = stmt.executeUpdate();
                // 提交事务
                conn.commit();
                return row;
            } catch (Exception e) {
                // 回滚事务
                conn.rollback();
                throw e;
            }
        }
    }

    private static void selectById(long id) throws SQLException {
        String sql = "SELECT `id`, `name`, `age`, `nick`, `create_time`, `update_time` FROM `user` WHERE `id` = ?";
        try (Connection conn = DBUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, id);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    System.out.print("id = " + resultSet.getLong("id"));
                    System.out.print(", name = " + resultSet.getString("name"));
                    System.out.print(", nick = " + resultSet.getString("nick"));
                    System.out.print(", age = " + resultSet.getInt("age"));
                    System.out.print(", create_time = " + resultSet.getTimestamp("create_time"));
                    System.out.println(", update_time = " + resultSet.getTimestamp("update_time"));
                }
            }
        }
    }

    /**
     * 批量插入数据
     */
    private static void batchInsert() throws SQLException {
        String sql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`)";
        List<Object> paramList = new ArrayList<>();
        paramList.add("Jordan");
        paramList.add(60);
        paramList.add("Jordan");
        paramList.add("2021-02-24 18:50:15");
        paramList.add("2021-02-24 18:50:15");

        paramList.add("Johnson");
        paramList.add(66);
        paramList.add("Johnson");
        paramList.add("2021-02-24 18:52:15");
        paramList.add("2021-02-24 18:52:15");

        paramList.add("Iverson");
        paramList.add(50);
        paramList.add("Iverson");
        paramList.add("2021-02-24 18:53:15");
        paramList.add("2021-02-24 18:53:15");

        paramList.add("James");
        paramList.add(36);
        paramList.add("James");
        paramList.add("2021-02-24 18:55:15");
        paramList.add("2021-02-24 18:55:15");

        paramList.add("T-MAC");
        paramList.add(40);
        paramList.add("T-MAC");
        paramList.add("2021-02-24 18:56:15");
        paramList.add("2021-02-24 18:56:15");
        DBUtils.executeBatchInsertPrepareStatement(sql, 5, 5, paramList);
    }

    private static void insert() throws SQLException {
        String sql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`) " +
                "VALUES(?, ?, ?, ?, ?)";
        List<Object> paramList = new ArrayList<>();
        paramList.add("Kobe");
        paramList.add(41);
        paramList.add("Kobe");
        paramList.add("2021-02-24 16:45:15");
        paramList.add("2021-02-24 16:45:15");
        DBUtils.executeUpdatePrepareStatement(sql, paramList);
    }

    private static void messageFormat() {
        String sql = "CREATE DATABASE IF NOT EXISTS {0} CHARACTER SET {1}";
        String format = MessageFormat.format(sql, "product", "utf8mb4");
        System.out.println(format);
    }

}
