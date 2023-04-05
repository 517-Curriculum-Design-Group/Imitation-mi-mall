package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.Comment;
import com.xiaomi_mall.enity.Order;
import com.xiaomi_mall.enity.Product;
import com.xiaomi_mall.enity.User;
import com.xiaomi_mall.mapper.CommentMapper;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.CommentService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.SkuAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    @Lazy
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ProductService productService;

    @Override
    public Result getBackCommentList(Integer pageNum, Integer pageSize) {

        List<Comment> commentList = commentService.list();
        List<Product> productList = productService.list();
        List<HashMap<String, Object>> res = new ArrayList<>();

        for (Comment comment: commentList)
        {
            HashMap<String, Object> map = new HashMap<>();
            map.put("commentId", comment.getCommentId());
            String productName = "";
            String productPic = "";
            for (Product product : productList)
            {
                if(product.getProductId() == comment.getProductId())
                {
                    productName = product.getProductName();
                    productPic = product.getProductPic();
                    break;
                }
            }
            map.put("productName", productName);
            map.put("productPic", productPic);
            map.put("content", comment.getContent());
            map.put("commentTime", comment.getCommentTime());
            res.add(map);
        }
        return Result.okResult(res);
    }
}
