package com.shardingsphere.demo.service;

import com.shardingsphere.demo.model.Order;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderService {

    @Autowired
    private DataSource datasource;

    public int createOrder(Order order) {
        String sql = "insert into t_order (order_no,user_id,product_id,pay_amount,order_status,expire_time) values (?,?,?,?,?,?)";
        try {
            Connection connection = datasource.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, order.getOrderNo());
            psmt.setLong(2, order.getUserId());
            psmt.setLong(3, order.getProductId());
            psmt.setInt(4, order.getPayAmount());
            psmt.setInt(5, order.getOrderStatus());
            psmt.setDate(6, order.getExpireTime());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteOrder(String orderNo) {
        String sql = "delete from t_order where order_no = " + orderNo;
        try {
            Connection connection = datasource.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, orderNo);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderStatus(String orderNo, int orderStatus) {
        String sql = "update t_order set order_status = " + orderStatus +  " where order_no = " + orderNo;
        try {
            Connection connection = datasource.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setInt(1, orderStatus);
            psmt.setString(2, orderNo);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Order getOrderByOrderNo(String orderNo) {
        Order order = new Order();
        String sql = "select create_time,order_no,user_id,product_id,pay_amount,order_status,expire_time from t_order where order_no = " + orderNo;

        try {
            Connection connection = datasource.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, orderNo);

            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()){
                order.setCreateTime(resultSet.getDate("create_time"));
                order.setOrderNo(resultSet.getString("order_no"));
                order.setUserId(resultSet.getLong("user_name"));
                order.setProductId(resultSet.getLong("user_name"));
                order.setPayAmount(resultSet.getInt("pay_amount"));
                order.setOrderStatus(resultSet.getInt("order_status"));
                order.setExpireTime(resultSet.getDate("expire_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
