package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("SELECT * FROM `Order`")
    List<Order> getOrderList();

    @Select("SELECT * FROM `Order` WHERE order_id = #{order_id}")
    Order getOrderByOrderId(Integer order_id);

    @Select("SELECT DATE(order_time) as order_date, COUNT(*) as order_count " +
            "FROM `Order` " +
            "WHERE order_time >= DATE_SUB(NOW(), INTERVAL 1 MONTH) " +
            "GROUP BY DATE(order_time)")
    List<Map<String, Object>> getOrderCountEachDay();
}