package com.takuya.demo.mapper;

import com.takuya.demo.model.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderWriteDao {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}
