package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.Category;
import com.xiaomi_mall.mapper.CategoryMapper;
import com.xiaomi_mall.mapper.ProductMapper;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.SkuService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.vo.CateProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private SkuService skuService;
    @Override
    public Result getCategoryList() {
       List<Category> categoryList = list();
       return Result.okResult(categoryList);
    }

    @Override
    public Result getCateProduct(Integer categoryId) {
        List<CateProductVo> res = new ArrayList<>();
        //根据传进来的categoryId查询它所对应的parentId
        Category category = categoryMapper.selectById(categoryId);
        Long parentId = category.getParentId();
        //判断parentId是否等于-1
        if (parentId == SystemConstants.ROOT_ID) {   //说明该分类是根类
            //可以将其categoryId当做parentId查询它的子分类
            List<Category> children = getChildren(categoryId);
            //查询每个分类下的商品
            for (Category item : children) {
                List<Category> allByCategory = categoryMapper.getAllByCategory(item.getCategoryId());
                for(Category temp : allByCategory)
                {
                    res.add(BeanCopyUtils.copyBean(temp, CateProductVo.class));
                }
            }
            return Result.okResult(res);
        }
        else {
            //直接根据categoryId查询商品
            List<Category> allByCategories = categoryMapper.getAllByCategory(categoryId);
            for(Category temp : allByCategories) {
                res.add(BeanCopyUtils.copyBean(temp, CateProductVo.class));
            }
            return Result.okResult(res);
        }
    }


    private List<Category> getChildren(Integer id) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, id);
        List<Category> categoryList = list(queryWrapper);
        return categoryList;
    }

}
