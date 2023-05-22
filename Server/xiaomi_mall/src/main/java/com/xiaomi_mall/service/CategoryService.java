package com.xiaomi_mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.AddCategoryNameDto;
import com.xiaomi_mall.enity.Category;

public interface CategoryService extends IService<Category> {

    Result getCategoryList();

    Result getCateProduct(String search);

    Result updateCategoryName(Category category);

    Result getCategoryName(Integer categoryId);

    Result addCategoryName(AddCategoryNameDto addCategoryNameDto);
}


