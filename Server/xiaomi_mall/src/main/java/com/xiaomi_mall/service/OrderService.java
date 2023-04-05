
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Order;

public interface OrderService extends IService<Order> {

    Result getBackOrderList(Integer pageNum, Integer pageSize);

    Result getOrderDetail(Integer orderId);
}


