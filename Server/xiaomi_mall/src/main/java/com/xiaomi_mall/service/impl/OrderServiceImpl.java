package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.*;
import com.xiaomi_mall.mapper.AddressMapper;
import com.xiaomi_mall.mapper.OrderDetailMapper;
import com.xiaomi_mall.mapper.OrderMapper;
import com.xiaomi_mall.mapper.UserMapper;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.vo.SkuValueDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserService userService;

    @Override
    public Result getBackOrderList(Integer pageNum, Integer pageSize) {

        List<Order> orderList = orderMapper.getOrderList();
        List<User> userList = userService.list();
        List<HashMap<String, Object>> res = new ArrayList<>();

        for (Order order: orderList)
        {
            HashMap<String, Object> map = new LinkedHashMap<>();
            map.put("orderId", order.getOrderId());
            map.put("orderTime", order.getOrderTime());
            map.put("totalPrice", order.getTotalPrice());
            map.put("userId", order.getUserId());
            String username = "";
            for (User user: userList)
            {
                if(user.getUserId() == order.getUserId())
                {
                    username += user.getUserName();
                    break;
                }
            }
            map.put("userName", username);
            map.put("status", order.getStatus());
            res.add(map);
        }
        return Result.okResult(res);
    }

    @Override
    public Result getOrderDetail(Integer orderId) {
        Order order = orderMapper.getOrderByOrderId(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.getDetailListByOrderId(orderId);
        Address address = addressMapper.selectById(order.getAddressId());

        HashMap<String, HashMap<String, Object>> res = new LinkedHashMap<>();

        //订单相关
        HashMap<String, Object> map1 = new LinkedHashMap<>();
        map1.put("orderId", order.getOrderId());
        map1.put("orderTime", order.getOrderTime());
        res.put("orderDetail", map1);

        //商品相关
        HashMap<String, Object> map2 = new LinkedHashMap<>();
        List<HashMap<String, Object>> productList = new ArrayList<>();
        for (OrderDetail orderDetail: orderDetailList)
        {
            HashMap<String, Object> map = new LinkedHashMap<>();
            map.put("productName", orderDetail.getProductName());
            map.put("skuName", orderDetail.getSkuName());
            map.put("skuImage", orderDetail.getSkuImage());
            map.put("skuPrice", orderDetail.getSkuPrice());
            map.put("skuQuantity", orderDetail.getSkuQuantity());
            productList.add(map);
        }
        map2.put("productList", productList);
        map2.put("totalPrice", order.getTotalPrice());
        res.put("productDetail", map2);

        //收货相关
        HashMap<String, Object> map3 = new LinkedHashMap<>();
        map3.put("recipientName", address.getRecipientName());
        map3.put("recipientPhone", address.getRecipientPhone());
        String specificAddress = address.getProvince() + address.getCity() + address.getDistrict() +
                address.getZhen() + address.getDetail();
        map3.put("specificAddress", specificAddress);
        res.put("addressDetail", map3);
        return Result.okResult(res);
    }
}