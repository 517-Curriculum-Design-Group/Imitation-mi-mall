package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT * FROM `Order`")
    List<Order> getOrderList();

    @Select("SELECT * FROM `Order` WHERE order_id = #{order_id}")
    Order getOrderByOrderId(Integer order_id);
}