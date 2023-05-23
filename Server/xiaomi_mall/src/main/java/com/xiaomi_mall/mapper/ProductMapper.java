package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    @Update("UPDATE Product SET sku_list = #{sku_list} WHERE product_id = #{product_id} AND del_flag = '0'")
    int modifySkuList(int product_id, String sku_list);

    @Update("UPDATE Product SET least_price = #{least_price} WHERE product_id = #{product_id} AND del_flag = '0'")
    int modifyLeastPrice(int product_id, String least_price);

    @Select("SELECT c.category_id,ANY_VALUE(p.product_id) AS product_id, c.category_name, p.product_name, ANY_VALUE(p" +
            ".product_description) AS " +
            "product_description, ANY_VALUE(p.product_pic) AS product_pic, MIN(p.least_price) AS least_price, ANY_VALUE(p.status) AS status \n" +
            "FROM Category c \n" +
            "LEFT JOIN Product p ON c.category_id = p.category_id \n" +
            "LEFT JOIN SKU s ON s.product_id = p.product_id \n" +
            "WHERE c.category_id = #{category_id} \n" +
            "GROUP BY p.product_name \n" +
            "ORDER BY least_price;\n")
    List<Product> getAllByCategory1(int category_id);
}

