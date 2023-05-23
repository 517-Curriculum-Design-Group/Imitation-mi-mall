
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Product;
import com.xiaomi_mall.enity.Seckill;
import com.xiaomi_mall.enity.Sku;

public interface SeckillService extends IService<Seckill> {

    Result seckill(Integer productId);

    void decrByStock(Integer productId);

    Result addSeckill(Seckill seckill);

    Result getSeckillProd();

    Result getSelectProd(Product product);

    Result getSeckillSku(int productId);

    Result getSelectSku(Sku sku);

    Result getSeckillList();

    Result getFollSecList();
}


