package com.xiaomi_mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.exception.enity.Product;
import com.xiaomi_mall.mapper.ProductMapper;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Result getProductDetail(Integer product_id) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getProductId, product_id);
        Product product = getOne(queryWrapper);

        ProductVo pv = new ProductVo();
        pv.setProductId(product.getProductId());
        pv.setCategoryId(product.getCategoryId());
        pv.setProductName(product.getProductName());
        pv.setSkuList(JSON.parseObject(product.getSkuList(), LinkedHashMap.class, Feature.OrderedField));
        pv.setProductDescription(product.getProductDescription());
        pv.setCreateTime(product.getCreateTime());

        return Result.okResult(pv);
    }
}
