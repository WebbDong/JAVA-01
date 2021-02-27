package lesson10.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 使用 JDBC 原生接口，实现数据库的增删改查操作。
 * @author Webb Dong
 * @date 2021-02-23 22:15
 */
public class JdbcCrud {

    public static void main(String[] args) throws Exception {
//        ddl();
//        dml();
        dql();
    }

    // ------------------------- DQL -----------------------------

    private static void dql() throws SQLException {
        selectAll();
        selectById(2L);
    }

    /**
     * 查询所有数据
     */
    private static void selectAll() throws SQLException {
        String sql = "SELECT `id`, `name`, `age`, `nick`, `create_time`, `update_time` FROM `user`";
        select(sql);
    }

    private static void selectById(long id) throws SQLException {
        String sql = "SELECT `id`, `name`, `age`, `nick`, `create_time`, `update_time` FROM `user` " +
                "WHERE `id` = " + id;
        select(sql);
    }

    private static void select(String sql) throws SQLException {
        try (Connection conn = DBUtils.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                ResultSet resultSet = stmt.executeQuery(sql);
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

    // ------------------------- DML -----------------------------

    private static void dml() throws SQLException {
        insert();
        update();
        delete();
    }

    /**
     * 插入数据
     */
    private static void insert() throws SQLException {
        String sql = "INSERT INTO `user`(`name`, `age`, `nick`, `create_time`, `update_time`) " +
                "VALUES('Michael Adam', 30, 'Adam', '2021-02-24 13:40:15', '2021-02-24 13:40:15')";
        DBUtils.executeStatement(sql);
        System.out.println("successfully inserted");
    }

    /**
     * 修改数据
     */
    private static void update() throws SQLException {
        String sql = "UPDATE `user` SET `name` = 'Lisa', `nick` = 'Lisa' WHERE `id` = 1";
        DBUtils.executeStatement(sql);
        System.out.println("successfully updated");
    }

    /**
     * 删除数据
     */
    private static void delete() throws SQLException {
        String sql = "DELETE FROM `user` WHERE `id` = 1";
        DBUtils.executeStatement(sql);
        System.out.println("successfully deleted");
    }

    // ------------------------- DDL -----------------------------

    /**
     * 数据定义语言
     */
    private static void ddl() throws SQLException {
        createDatabase();
        createTable();
        alterTable();
        dropTable();
    }

    /**
     * 创建数据库
     */
    private static void createDatabase() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS jdbc CHARACTER SET utf8mb4";
        DBUtils.executeDatabaseStatement(sql);
    }

    /**
     * 创建表
     */
    private static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `user`(" +
                "`id` BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "`name` VARCHAR(20)," +
                "`age` INT," +
                "`create_time` TIMESTAMP," +
                "`update_time` TIMESTAMP)";
        DBUtils.executeUpdateStatement(sql);
        System.out.println("successfully created");
    }

    /**
     * 修改表结构
     */
    private static void alterTable() throws SQLException {
        String sql = "ALTER TABLE `user` " +
                "ADD COLUMN `nick` VARCHAR(10)";
        DBUtils.executeUpdateStatement(sql);
        System.out.println("successfully altered");
    }

    /**
     * 删除表
     */
    private static void dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS `user`";
        DBUtils.executeUpdateStatement(sql);
        System.out.println("successfully dropped");
    }

}
