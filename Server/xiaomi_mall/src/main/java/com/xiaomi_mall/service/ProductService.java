
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Product;

public interface ProductService extends IService<Product> {

    Result getProductDetail(Integer product_id);

    Result getProductList(Integer pageNum, Integer pageSize, String productName);

    Result deleteCategory(Integer categoryId);
}


