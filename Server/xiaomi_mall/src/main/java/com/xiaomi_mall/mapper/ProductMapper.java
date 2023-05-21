package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import springfox.documentation.spring.web.json.Json;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    @Update("UPDATE Product SET sku_list = #{sku_list} WHERE product_id = #{product_id} AND del_flag = '0'")
    int modifySkuList(int product_id, String sku_list);

    @Update("UPDATE Product SET least_price = #{least_price} WHERE product_id = #{product_id} AND del_flag = '0'")
    int modifyLeastPrice(int product_id, String least_price);
}

