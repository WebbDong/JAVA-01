package lesson10.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC 工具类
 * @author Webb Dong
 * @date 2021-02-24 01:16
 */
public class DBUtils {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(String url, String root, String password) throws SQLException {
        return DriverManager.getConnection(url, root, password);
    }

    public static void executeUpdate(String sql, String url, String user, String password) throws SQLException {
        try (Connection conn = DBUtils.getConnection(url, user, password)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            }
        }
    }

}
