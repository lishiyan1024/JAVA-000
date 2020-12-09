package com.shardingsphere.demo.model;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {

    private Long id;
    private Date createTime;
    private Date updateTime;
    private String orderNo;
    private Long userId;
    private Long productId;
    private int payAmount;
    private int orderStatus;
    private Date expireTime;
}
