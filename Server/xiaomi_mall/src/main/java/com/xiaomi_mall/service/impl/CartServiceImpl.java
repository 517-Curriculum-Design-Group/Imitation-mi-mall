package com.xiaomi_mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Address;
import com.xiaomi_mall.enity.Cart;
import com.xiaomi_mall.enity.Order;
import com.xiaomi_mall.enity.OrderDetail;
import com.xiaomi_mall.mapper.AddressMapper;
import com.xiaomi_mall.mapper.CartMapper;
import com.xiaomi_mall.service.CartService;
import com.xiaomi_mall.service.OrderDetailService;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Result getCartList(HttpServletRequest request) {
        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        QueryWrapper<Cart> cartListWrapper = new QueryWrapper<>();
        cartListWrapper
                .eq("user_id", userId)
                .eq("del_flag", 0);
        List<Map<String, Object>> maps = cartMapper.selectMaps(cartListWrapper);
        for (Map<String, Object> m:maps)
        {
            String skuName = m.get("sku_name").toString();
            LinkedHashMap skuJsonMap = JSON.parseObject(skuName, LinkedHashMap.class, Feature.OrderedField);
            m.put("sku_name", skuJsonMap);

            m.remove("user_id");
            m.remove("del_flag");
        }

        return Result.okResult(maps);
    }






}
