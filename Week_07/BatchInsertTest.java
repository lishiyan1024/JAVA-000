package com.yuanweixc.web;

import com.yuanweixc.web.utils.RandomUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BatchInsertTest {

    private static final String DB_URL = "jdbc:mysql://${MYSQL_HOST:localhost}:3306/db_mall";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static final String SQL = "insert into geektime_mall_order (order_no,user_id,product_id," +
            "pay_amount,original_amount,discount_amount,order_status,expire_time) values (?,?,?,?,?,?,?,?)";

    public static void main(String[] args) {
//        singleInsert();
//        batchAllInsert();
        batchThousandCycleInsert();
    }

    private static void singleInsert() {
        Connection connection = null;
        PreparedStatement psmt = null;

        long start = System.currentTimeMillis();
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            psmt = connection.prepareStatement(SQL);
            connection.setAutoCommit(false); // 将连接的自动提交关闭，数据在传送到数据库的过程中相当耗时
            for (int i = 0; i < 1000000; i++) {
                psmt.setString(1, generateOrderNo());
                psmt.setInt(2, new Random().nextInt(1000));
                psmt.setInt(3, new Random().nextInt(100));

                int originAmount = new Random().nextInt(1000) + 10;
                psmt.setInt(4, originAmount);
                psmt.setInt(5, originAmount);
                psmt.setInt(6, 0);
                psmt.setInt(7, 400);
                psmt.setDate(8, new java.sql.Date(DateUtils.addMinutes(new Date(), 1200).getTime()));
                psmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time：" + (end - start));
    }

    private static void batchAllInsert(){
        Connection connection = null;
        PreparedStatement psmt = null;
        long start = System.currentTimeMillis();
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            psmt = connection.prepareStatement(SQL);
            connection.setAutoCommit(false);
            for (int i = 0; i < 1000000; i++) {
                psmt.setString(1, generateOrderNo());
                psmt.setInt(2, new Random().nextInt(1000));
                psmt.setInt(3, new Random().nextInt(100));

                int originAmount = new Random().nextInt(1000) + 10;
                psmt.setInt(4, originAmount);
                psmt.setInt(5, originAmount);
                psmt.setInt(6, 0);
                psmt.setInt(7, 400);
                psmt.setDate(8, new java.sql.Date(DateUtils.addMinutes(new Date(), 1200).getTime()));
                psmt.addBatch();
            }
            psmt.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time：" + (end - start));
    }

    private static void batchThousandCycleInsert(){
        Connection connection = null;
        PreparedStatement psmt = null;
        long start = System.currentTimeMillis();
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            psmt = connection.prepareStatement(SQL);
            connection.setAutoCommit(false);
            for (int j = 0; j < 1000; j++){
                for (int i = 0; i < 1000; i++) {
                    psmt.setString(1, generateOrderNo());
                    psmt.setInt(2, new Random().nextInt(1000));
                    psmt.setInt(3, new Random().nextInt(100));

                    int originAmount = new Random().nextInt(1000) + 10;
                    psmt.setInt(4, originAmount);
                    psmt.setInt(5, originAmount);
                    psmt.setInt(6, 0);
                    psmt.setInt(7, 400);
                    psmt.setDate(8, new java.sql.Date(DateUtils.addMinutes(new Date(), 1200).getTime()));
                    psmt.addBatch();
                }
                psmt.executeBatch();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("cost time：" + (end - start));
    }

    private static String generateOrderNo() {
        String randomFigure = RandomUtil.randomFigure(8);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date now = new Date();
        String dateString = dateFormat.format(now);
        return "1001" + dateString + randomFigure;
    }
}
