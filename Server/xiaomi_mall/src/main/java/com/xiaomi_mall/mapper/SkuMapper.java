package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Sku;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    @Select("SELECT * FROM SKU WHERE product_id = #{product_id} AND del_flag = '0'")
    List<Sku> getSkuListByProductId(int product_id);

    @Update("UPDATE SKU SET sku_name = #{sku_name} WHERE sku_id = #{sku_id}")
    void modifySkuName(int sku_id, String sku_name);


    @Update("UPDATE SKU SET del_flag = #{del_flag} WHERE sku_id = #{sku_id}")
    void modifySkuStatus(Integer sku_id, int del_flag);
}
