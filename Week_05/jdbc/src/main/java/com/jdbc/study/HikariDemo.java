package com.jdbc.study;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class HikariDemo {

    public static void main(String[] args) {
        HikariDemo.transaction();
    }

    private static Connection getConn(){
        //配置文件
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:8888/test?characterEncoding=utf-8");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("123456");
        hikariConfig.setMaximumPoolSize(8);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "256");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(hikariConfig);
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void transaction() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            conn = getConn();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO car (plate_number, brand, color) VALUES (?,?,?)";
            st = conn.prepareStatement(sql);
            for (int i = 1; i < 108; i++) {
                st.setString(1, "粤B1234" + new Random().nextInt(88888) + 10000);
                st.setString(2, "BMW");
                st.setString(3, "WHITE");
                st.addBatch();
                if (i % 10 == 0) {
                    st.executeBatch();
                    st.clearBatch();
                }
            }
            st.executeBatch();

            String sql2 = "INSERT INTO user_info (name, gender, age) VALUES (?,?,?)";
            st = conn.prepareStatement(sql2);
            st.setString(1, "粤B00001");
            st.setString(2, "Benz");
            st.setString(3, "BLACK" );
            st.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
