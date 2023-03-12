package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.exception.enity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}