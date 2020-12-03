package com.nero.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createTime;
    private Date updateTime;
    private String orderNo;
    private Long userId;
    private Long productId;
    private int payAmount;
    private int originAmount;
    private int discountAmount;
    private int orderStatus;
    private Date expireTime;
}
