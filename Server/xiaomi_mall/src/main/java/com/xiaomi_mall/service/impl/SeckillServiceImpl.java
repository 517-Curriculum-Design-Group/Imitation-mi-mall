package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.exception.enity.Seckill;
import com.xiaomi_mall.mapper.SeckillMapper;
import com.xiaomi_mall.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {
    @Autowired
    private SeckillMapper seckillMapper;

}
