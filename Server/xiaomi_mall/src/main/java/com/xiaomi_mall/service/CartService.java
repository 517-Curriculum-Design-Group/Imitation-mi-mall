package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CartService extends IService<Cart> {

    Result getCartList(HttpServletRequest request);
}


