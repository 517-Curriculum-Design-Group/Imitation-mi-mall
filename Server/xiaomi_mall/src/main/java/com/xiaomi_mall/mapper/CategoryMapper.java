package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("SELECT c.category_id,ANY_VALUE(p.product_id) AS product_id, c.category_name, p.product_name, ANY_VALUE(p" +
            ".product_description) AS " +
            "product_description, ANY_VALUE(s.sku_image) AS sku_image, MIN(s.sku_price) AS sku_price \n" +
            "FROM Category c \n" +
            "LEFT JOIN Product p ON c.category_id = p.category_id \n" +
            "LEFT JOIN SKU s ON s.product_id = p.product_id \n" +
            "WHERE c.category_id = #{category_id} \n" +
            "GROUP BY p.product_name \n" +
            "ORDER BY sku_price;\n")
    List<Category> getAllByCategory(Integer category_id);
}
