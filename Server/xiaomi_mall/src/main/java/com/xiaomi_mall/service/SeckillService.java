
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Seckill;

public interface SeckillService extends IService<Seckill> {

    Result seckill(Integer productId);

    void decrByStock(Integer productId);
}


