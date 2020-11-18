package com.jdbc.study;

import java.sql.*;
import java.util.Random;

public class TransactionDemo {

    public static void main(String[] args) {
        TransactionDemo.transaction();
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
        } finally {
            release(rs, st, conn);
        }
    }

    public void transactionCallBack() throws SQLException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            conn.setAutoCommit(false);

            String sql1 = "update account set money=money-100 where name='a'";
            st = conn.prepareStatement(sql1);
            st.executeUpdate();

            int x = 1 / 0;

            String sql2 = "update account set money=money+100 where name='b'";
            st = conn.prepareStatement(sql2);
            st.executeUpdate();

            conn.commit();
        } finally {
            release(rs, st, conn);
        }
    }

    private static Connection getConn(){
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:8888/test?characterEncoding=utf-8";
        String username = "root";
        String password = "123456";
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void release(ResultSet rs, PreparedStatement statement, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
