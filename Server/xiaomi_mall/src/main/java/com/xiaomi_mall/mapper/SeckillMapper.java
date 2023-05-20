package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SeckillMapper extends BaseMapper<Seckill> {

    @Update("update Seckill set stock_count = stock_count - 1 where product_id = #{product_id}")
    boolean decraystore(Integer product_id);

}
