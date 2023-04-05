package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {


    @Select("SELECT * FROM OrderDetail WHERE order_id = #{order_id}")
    List<OrderDetail> getDetailListByOrderId(Integer order_id);
}
