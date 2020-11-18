package com.jdbc.study;

import java.sql.*;

public class JdbcCrudDemo {

    public static void main(String[] args) {
        insert();
        delete(1);
        update();
        query();
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

    private static int insert() {
        Connection conn = getConn();
        int i = 0;;
        String sql = "INSERT INTO car (plate_number, brand, color) VALUES (?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "粤B12345");
            pstmt.setString(2, "BMW");
            pstmt.setString(3, "WHITE");
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static int update() {
        Connection conn = getConn();
        int i = 0;
        String sql = "update car set color='RED' where plate_number = '粤B12345' ";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("Update resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static Integer query() {
        Connection conn = getConn();
        String sql = "select * from car where plate_number = '粤B12345'";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("***************************");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("***************************");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int delete(int id) {
        Connection conn = getConn();
        int i = 0;
        String sql = "delete from car where id = " + id;
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("Delete result: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
