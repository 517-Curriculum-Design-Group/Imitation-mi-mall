package com.xiaomi_mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.Product;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.mapper.CategoryMapper;
import com.xiaomi_mall.mapper.ProductMapper;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.vo.ProductListVo;
import com.xiaomi_mall.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

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

    @Override
    public Result getProductList(Integer pageNum, Integer pageSize, String productName) {
        //构造器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //判断前端是否传入productName字段
        queryWrapper.like(Objects.nonNull(productName), Product::getProductName, productName);
        //分页
        Page<Product> pageInfo = new Page<>(pageNum, pageSize);
        //分页查询
        page(pageInfo, queryWrapper);
        //获取到Product的数据
        List<Product> productList = pageInfo.getRecords();
        //封装到vo类
        List<ProductListVo> productListVos = toProductListVo(productList);

        return Result.okResult(productListVos);

    }

    @Override
    public Result deleteCategory(Integer categoryId) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        Long parentId = categoryService.getById(categoryId).getParentId();
        if (parentId == SystemConstants.ROOT_ID) {
            int size = categoryMapper.getChildrenCate(categoryId).size();
            if (size > SystemConstants.CATE_CAN_DELETE) {
                throw new SystemException(AppHttpCodeEnum.CHILDREN_CATEGORY_EXIST);
            }
            return Result.okResult();
        }
        else {
            queryWrapper.eq(Product::getCategoryId, categoryId);
            int count = count(queryWrapper);
            if (count > SystemConstants.CATE_CAN_DELETE) {
                throw new SystemException(AppHttpCodeEnum.PRODUCT_EXIST);
            }
            return Result.okResult();
        }

    }

    @Override
    public Result addProductList(Product product)
    {



        return null;
    }

    private List<ProductListVo> toProductListVo(List<Product> list) {
        List<ProductListVo> productListVos = BeanCopyUtils.copyBeanList(list, ProductListVo.class);
        //查询productListVos
        for (ProductListVo vo : productListVos) {
            String categoryName = categoryService.getById(vo.getCategoryId()).getCategoryName();
            vo.setCategoryName(categoryName);
        }
        return productListVos;
    }
}
