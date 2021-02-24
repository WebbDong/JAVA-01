package lesson10.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用 JDBC 原生接口，实现数据库的增删改查操作。
 * @author Webb Dong
 * @date 2021-02-23 22:15
 */
public class JdbcCrud {

    private static final String URL = "jdbc:mysql://localhost:33006/test";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws Exception {
        ddl();
    }

    // ------------------------- DDL -----------------------------

    private static void ddl() throws SQLException {
        createDatabase();
        createTable();
//        alterTable();
    }

    private static void createDatabase() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS test CHARACTER SET utf8mb4";
        DBUtils.executeUpdate(sql, URL, USER, PASSWORD);
    }

    /**
     * 创建表
     * @throws SQLException
     */
    private static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `user`(" +
                "`id` BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "`name` VARCHAR(20)," +
                "`age` INT," +
                "`create_time` TIMESTAMP," +
                "`update_time` TIMESTAMP)";
        DBUtils.executeUpdate(sql, URL, USER, PASSWORD);
    }

    private static void alterTable() throws SQLException {
        String sql = "ALTER TABLE `user` " +
                "ADD COLUMN `nick` VARCHAR(10)";

        DBUtils.executeUpdate(sql, URL, USER, PASSWORD);
    }

}
