package com.takuya.demo.mapper;

import com.takuya.demo.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderReadDao {

    Order selectByPrimaryKey(Long id);

    List<Order> getList();
}
