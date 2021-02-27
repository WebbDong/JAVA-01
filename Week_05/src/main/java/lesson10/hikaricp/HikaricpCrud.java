package lesson10.hikaricp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 使用 Hikaricp 连接池 CRUD
 * @author Webb Dong
 * @date 2021-02-25 12:58
 */
public class HikaricpCrud {

    public static void main(String[] args) throws SQLException {
//        ddl();
//        dml();
        dql();
    }

    // ------------------------- DQL -----------------------------

    private static void dql() throws SQLException {
        selectAll();
        selectById(30L);
    }

    private static void selectById(long id) throws SQLException {
        String sql = "SELECT `id`, `name`, `age`, `nick`, `create_time`, `update_time`" +
                " FROM `hikaricp`.`user` WHERE `id` = ?";
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
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

    private static void selectAll() throws SQLException {
        String sql = "SELECT `id`, `name`, `age`, `nick`, `create_time`, `update_time` FROM `hikaricp`.`user`";
        try (Connection conn = HikaricpUtils.INSTANCE.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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

    // ------------------------- DML -----------------------------

    private static void dml() throws SQLException {
        insert();
        batchInsert();
        update();
        batchUpdate();
        delete();
        batchDelete();
    }

    private static void batchDelete() throws SQLException {
        String sql = "DELETE FROM `hikaricp`.`user` WHERE `id` = ?";
        List<Object> paramList = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            paramList.add(i);
        }
        List<Integer> retList = HikaricpUtils.INSTANCE.executeBatch(sql, 1, paramList, 2000);
        System.out.println(retList);
    }

    private static void delete() throws SQLException {
        String sql = "DELETE FROM `hikaricp`.`user` WHERE `id` = ?";
        List<Object> paramList = new ArrayList<>();
        paramList.add(2);
        HikaricpUtils.INSTANCE.executeUpdate(sql, paramList);
    }

    private static void batchUpdate() throws SQLException {
        String sql = "UPDATE `hikaricp`.`user` SET `name` = ?, `nick` = ?, `update_time` = ? WHERE `id` = ?";
        List<Object> paramList = new ArrayList<>();
        Timestamp updateTimestamp = new Timestamp(new Date().getTime());
        for (int i = 0; i < 10; i++) {
            paramList.add("new name" + i);
            paramList.add("new name" + i);
            paramList.add(updateTimestamp);
            paramList.add(i + 1);
        }
        HikaricpUtils.INSTANCE.executeBatch(sql, 4, paramList, 2000);
    }

    private static void update() throws SQLException {
        String sql = "UPDATE `hikaricp`.`user` SET `name` = ?, `nick` = ?, `update_time` = ? WHERE `id` = ?";
        List<Object> paramList = new ArrayList<>();
        paramList.add("Kobe");
        paramList.add("Kobe");
        paramList.add(new Timestamp(new Date().getTime()));
        paramList.add(1);
        HikaricpUtils.INSTANCE.executeUpdate(sql, paramList);
    }

    private static void batchInsert() throws SQLException {
        String sql = "INSERT INTO `hikaricp`.`user`(`name`, `age`, `nick`, `create_time`, `update_time`) " +
                "VALUES(?, ?, ?, ?, ?)";
        List<Object> paramList = new ArrayList<>();
        Timestamp stamp = new Timestamp(new Date().getTime());
        for (long id = 1; id < 10000; id++) {
            paramList.add("user" + id);
            paramList.add((10 + id) % 90);
            paramList.add("user" + id);
            paramList.add(stamp);
            paramList.add(stamp);
        }
        HikaricpUtils.INSTANCE.executeBatch(sql, 5, paramList, 2000);
    }

    private static void insert() throws SQLException {
        String sql = "INSERT INTO `hikaricp`.`user`(`name`, `age`, `nick`, `create_time`, `update_time`) " +
                "VALUES('Michael Adam', 30, 'Adam', '2021-02-24 13:40:15', '2021-02-24 13:40:15')";
        HikaricpUtils.INSTANCE.executeUpdate(sql);
        System.out.println("successfully inserted");
    }

    // ------------------------- DDL -----------------------------

    private static void ddl() throws SQLException {
        createDatabase();
        createTable();
        alterTable();
        dropTable();
    }

    private static void dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS `hikaricp`.`user`";
        HikaricpUtils.INSTANCE.executeUpdate(sql);
    }

    private static void alterTable() throws SQLException {
        String sql = "ALTER TABLE `hikaricp`.`user` " +
                "ADD COLUMN `nick` VARCHAR(10)";
        HikaricpUtils.INSTANCE.executeUpdate(sql);
    }

    private static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS `hikaricp`.`user`(" +
                "`id` BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "`name` VARCHAR(20)," +
                "`age` INT," +
                "`create_time` TIMESTAMP," +
                "`update_time` TIMESTAMP)";
        HikaricpUtils.INSTANCE.executeUpdate(sql);
    }

    private static void createDatabase() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS hikaricp CHARACTER SET utf8mb4";
        HikaricpUtils.INSTANCE.executeUpdate(sql);
    }

}
