package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.exception.enity.Category;

public interface CategoryService extends IService<Category> {

    Result getCategoryList();

    Result getCateProduct(Integer categoryId);
}


