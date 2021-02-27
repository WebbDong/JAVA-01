package lesson10.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * JDBC 工具类
 * @author Webb Dong
 * @date 2021-02-24 01:16
 */
public class DBUtils {

    private static final String URL = "jdbc:mysql://192.168.238.150:33006/";

    private static final String DB_URL = URL + "jdbc";

    private static final String USER = "root";

    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static int executeDatabaseStatement(String sql) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                return stmt.executeUpdate(sql);
            }
        }
    }

    public static int executeUpdateStatement(String sql) throws SQLException {
        try (Connection conn = DBUtils.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                return stmt.executeUpdate(sql);
            }
        }
    }

    public static boolean executeStatement(String sql) throws SQLException {
        try (Connection conn = DBUtils.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                return stmt.execute(sql);
            }
        }
    }

    public static int executeUpdatePrepareStatement(String sql, List<Object> paramList) throws SQLException {
        try (Connection conn = DBUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (paramList != null) {
                    for (int i = 0, size = paramList.size(); i < size; i++) {
                        stmt.setObject(i + 1, paramList.get(i));
                    }
                }
                return stmt.executeUpdate();
            }
        }
    }

    public static int executeBatchInsertPrepareStatement(String sql,
                                                         int row,
                                                         int column,
                                                         List<Object> paramList) throws SQLException {
        if (paramList == null || paramList.size() == 0) {
            return 0;
        }

        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append(sql).append(" VALUES");
        for (int i = 0, lastIndex = row - 1; i < row; i++) {
            StringBuilder valueSB = new StringBuilder();
            valueSB.append("(");
            for (int j = 0, lastIndex2 = column - 1; j < column; j++) {
                valueSB.append(j != lastIndex2 ? "?," : "?");
            }
            valueSB.append((i != lastIndex ? ")," : ")"));
            sqlSB.append(valueSB);
        }

        try (Connection conn = DBUtils.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sqlSB.toString())) {
                for (int i = 0, size = paramList.size(); i < size; i++) {
                    stmt.setObject(i + 1, paramList.get(i));
                }
                return stmt.executeUpdate();
            }
        }
    }

}
