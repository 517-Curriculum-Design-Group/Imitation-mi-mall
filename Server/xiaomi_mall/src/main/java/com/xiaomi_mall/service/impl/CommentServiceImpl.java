package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.Comment;
import com.xiaomi_mall.enity.Product;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.mapper.CommentMapper;
import com.xiaomi_mall.service.CommentService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.vo.BackCommentVo;
import com.xiaomi_mall.vo.CommentVo;
import com.xiaomi_mall.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    @Lazy
    private CommentService commentService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Override
    public Result getBackCommentList(Integer pageNum, Integer pageSize, String rate) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Objects.nonNull(rate), Comment::getRate, rate);

        Page<Comment> pageInfo = new Page<>(pageNum, pageSize);

        page(pageInfo, queryWrapper);

        List<Comment> records = pageInfo.getRecords();

        List<BackCommentVo> backCommentVos = BeanCopyUtils.copyBeanList(records, BackCommentVo.class);

        for (BackCommentVo backCommentVo : backCommentVos ) {
            Product product = productService.getById(backCommentVo.getProductId());
            backCommentVo.setProductName(product.getProductName());
            backCommentVo.setProductPic(product.getProductPic());
        }

        return Result.okResult(backCommentVos);


//        List<Comment> commentList = commentService.list();
//        List<Product> productList = productService.list();
//        List<HashMap<String, Object>> res = new ArrayList<>();
//
//        for (Comment comment: commentList)
//        {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("commentId", comment.getCommentId());
//            String productName = "";
//            String productPic = "";
//            for (Product product : productList)
//            {
//                if(product.getProductId() == comment.getProductId())
//                {
//                    productName = product.getProductName();
//                    productPic = product.getProductPic();
//                    break;
//                }
//            }
//            map.put("productName", productName);
//            map.put("productPic", productPic);
//            map.put("content", comment.getContent());
//            map.put("commentTime", comment.getCommentTime());
//            res.add(map);
//        }
//        return Result.okResult(res);
    }

    @Override
    public Result commentList(int productId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对productId进行判断
        queryWrapper.eq(Comment::getProductId, productId);
        //根评论rookId为-1
        queryWrapper.eq(Comment::getParentId, SystemConstants.ROOT_ID);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getCommentId());
            //赋值
            commentVo.setChildren(children);
        }
        PageVo pageVo = new PageVo(commentVoList, page.getTotal());
        return Result.okResult(pageVo);
    }

    @Override
    public Result addcomment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return Result.okResult("评论成功");
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合
        for (CommentVo commentVo : commentVoList) {
            //通过createBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getUserId()).getNickName();
            commentVo.setNickName(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId() != -1){
                String toCommentNickName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentNickName(toCommentNickName);
            }else {
                commentVo.setToCommentNickName(nickName);
            }
        }
        return commentVoList;
    }
    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param commentId 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(int commentId) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, commentId);
        queryWrapper.orderByAsc(Comment::getCommentTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }
}
