package util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * HikariCP 工具类
 * @author Webb Dong
 * @date 2021-02-25 13:23
 */
public enum HikaricpUtils {

    INSTANCE;

    private final HikariDataSource dataSource;

    @SneakyThrows
    HikaricpUtils() {
//        dataSource = new HikariDataSource(new HikariConfig("lesson10/hikaricp/hikaricp.properties"));

        Properties properties = new Properties();
        properties.load(HikaricpUtils.class.getClassLoader()
                .getResourceAsStream("hikaricp.properties"));
//        properties.put("dataSource.logWriter", new PrintWriter(System.out));
        dataSource = new HikariDataSource(new HikariConfig(properties));
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public int executeUpdate(String sql) throws SQLException {
        try (Connection conn = getDataSource().getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                return stmt.executeUpdate();
            }
        }
    }

    public int executeUpdate(String sql, List<Object> paramList) throws SQLException {
        try (Connection conn = getDataSource().getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0, size = paramList.size(); i < size; i++) {
                    stmt.setObject(i + 1, paramList.get(i));
                }
                return stmt.executeUpdate();
            }
        }
    }

    /**
     * 批量处理
     * @param sql sql 语句
     * @param paramCountPreBatch 每个批次有多少个参数
     * @param paramList 参数列表
     * @param commitPreCount 达到多少数据后提交一批数据
     */
    public List<Integer> executeBatch(String sql, int paramCountPreBatch,
                                      List<Object> paramList, int commitPreCount) throws SQLException {
        if (paramList == null) {
            return null;
        }

        List<Integer> retList = new ArrayList<>(paramList.size());
        int[] retInts;
        try (Connection conn = getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0, size = paramList.size(); i < size;) {
                    for (int j = 0; i < size && j < paramCountPreBatch; j++) {
                        stmt.setObject(j + 1, paramList.get(i++));
                    }
                    stmt.addBatch();

                    if (i % commitPreCount == 0) {
                        retInts = stmt.executeBatch();
                        Arrays.stream(retInts).forEach(integer -> retList.add(integer));
                    }
                }
                retInts = stmt.executeBatch();
                Arrays.stream(retInts).forEach(i -> retList.add(i));
                return retList;
            }
        }
    }

    /**
     * 批量处理，手动控制事务
     * @param sql sql 语句
     * @param stmt PreparedStatement
     * @param paramCountPreBatch 每个批次有多少个参数
     * @param paramList 参数列表
     * @param commitPreCount 达到多少数据后提交一批数据
     */
    public List<Integer> executeBatchByManualTransaction(String sql,
                                                         PreparedStatement stmt,
                                                         int paramCountPreBatch,
                                                         List<Object> paramList,
                                                         int commitPreCount) throws SQLException {
        if (paramList == null) {
            return null;
        }

        List<Integer> retList = new ArrayList<>(paramList.size());
        int[] retInts;
        for (int i = 0, size = paramList.size(); i < size;) {
            for (int j = 0; i < size && j < paramCountPreBatch; j++) {
                stmt.setObject(j + 1, paramList.get(i++));
            }
            stmt.addBatch();

            if (i % commitPreCount == 0) {
                retInts = stmt.executeBatch();
                Arrays.stream(retInts).forEach(integer -> retList.add(integer));
            }
        }
        retInts = stmt.executeBatch();
        Arrays.stream(retInts).forEach(i -> retList.add(i));
        return retList;
    }

    public int executeBatchInsertByValues(String sql, int row, int column,
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

        try (Connection conn = getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sqlSB.toString())) {
                for (int i = 0, size = paramList.size(); i < size; i++) {
                    stmt.setObject(i + 1, paramList.get(i));
                }
                return stmt.executeUpdate();
            }
        }
    }

    public int executeBatchInsertByValuesAndManualTransaction(PreparedStatement stmt,
                                                              List<Object> paramList) throws SQLException {
        if (paramList == null || paramList.size() == 0) {
            return 0;
        }
        for (int i = 0, size = paramList.size(); i < size; i++) {
            stmt.setObject(i + 1, paramList.get(i));
        }
        return stmt.executeUpdate();
    }

    public String generateFullInsertByValuesSql(String sql, int row, int column) {
        StringBuilder sqlSB = new StringBuilder();
        sqlSB.append(sql).append(" VALUES ");
        for (int i = 0, lastIndex = row - 1; i < row; i++) {
            StringBuilder valueSB = new StringBuilder();
            valueSB.append("(");
            for (int j = 0, lastIndex2 = column - 1; j < column; j++) {
                valueSB.append(j != lastIndex2 ? "?," : "?");
            }
            valueSB.append((i != lastIndex ? ")," : ")"));
            sqlSB.append(valueSB);
        }
        return sqlSB.toString();
    }

}
