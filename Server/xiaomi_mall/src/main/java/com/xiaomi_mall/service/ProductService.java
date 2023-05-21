
package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.AddProductStockDto;
import com.xiaomi_mall.dto.ModifyProductStatusDto;
import com.xiaomi_mall.dto.ModifySkuDetailDto;
import com.xiaomi_mall.enity.Product;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ProductService extends IService<Product> {

    Result getProductDetail(HttpServletRequest request, Integer product_id);

    Result getProductList(Integer pageNum, Integer pageSize, String productName);

    Result deleteCategory(Integer categoryId);

    Result addNewProduct(Map<String, Object> map);

    Result modifyProductSku(ModifySkuDetailDto modifySkuDetailDto) throws UnsupportedEncodingException;

    Result getProductSku(Integer productId);

    Result addSkuToCart(HttpServletRequest request, Map<String, Object> map);

    Result getProductPrice(Map<String, Object> map);

    Result addProductToFavorite(HttpServletRequest request, Integer product_id);

    Result ModifyProductStatus(ModifyProductStatusDto modifyProductStatusDto);

    Result addProductStock(AddProductStockDto addProductStockDto);

    Result getFavoriteList(HttpServletRequest request);

    Result deleteProductToFavorite(HttpServletRequest request, Integer product_id);
}


