
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ProductService extends IService<Product> {

    Result getProductDetail(Integer product_id);

    Result getProductList(Integer pageNum, Integer pageSize, String productName);

    Result deleteCategory(Integer categoryId);

    Result addNewProduct(Map<String, Object> map);

    Result modifyProductSku(Map<String, Object> map);

    Result getProductSku(Integer productId);

    Result addSkuToCart(HttpServletRequest request, Integer sku_id);

}


