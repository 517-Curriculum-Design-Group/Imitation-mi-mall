
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.GenerateOrderDto;
import com.xiaomi_mall.dto.ModifyAddressInOrderDto;
import com.xiaomi_mall.dto.OrderCommit;
import com.xiaomi_mall.dto.SeckillOrderDto;
import com.xiaomi_mall.enity.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService extends IService<Order> {

    Result getBackOrderList(Integer pageNum, Integer pageSize, Integer status);

    Result getOrderDetail(Integer orderId);

    Result getUserOrderDetail(HttpServletRequest request, Integer orderId);

    Result checkOrder(HttpServletRequest request);

    Result generateOrder(HttpServletRequest request, GenerateOrderDto generateOrderDto);

    Result getOrderList(HttpServletRequest request);

    Result orderDelivery(List<Integer> orderId);


    void createSeckillOrder(SeckillOrderDto seckillOrderDto);

    Result ModifyAddressInOrder(HttpServletRequest request, ModifyAddressInOrderDto modifyAddressInOrderDto);
}


