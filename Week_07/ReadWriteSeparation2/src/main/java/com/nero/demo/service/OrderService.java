package com.nero.demo.service;

import com.nero.demo.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private DataSource datasource;

    public int createOrder(Order order) {
        String sql = "insert into geektime_mall_order (order_no,user_id,product_id,pay_amount,original_amount," +
                "discount_amount,order_status,expire_time) values (?,?,?,?,?,?,?,?)";
        try {
            Connection connection = datasource.getConnection();
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, order.getOrderNo());
            psmt.setLong(2, order.getUserId());
            psmt.setLong(3, order.getProductId());
            psmt.setInt(4, order.getPayAmount());
            psmt.setInt(5, order.getOriginAmount());
            psmt.setInt(6, order.getDiscountAmount());
            psmt.setInt(7, order.getOrderStatus());
            psmt.setDate(8, order.getExpireTime());
            return psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Order getOrderByOrderNo(String orderNo) {
        Order order = new Order();
        String sql = "select create_time,order_no,user_id,product_id,pay_amount,original_amount,discount_amount,order_status," +
                "expire_time from geektime_mall_order where order_no = " + orderNo;

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
                order.setOriginAmount(resultSet.getInt("original_amount"));
                order.setDiscountAmount(resultSet.getInt("discount_amount"));
                order.setOrderStatus(resultSet.getInt("order_status"));
                order.setExpireTime(resultSet.getDate("expire_time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
