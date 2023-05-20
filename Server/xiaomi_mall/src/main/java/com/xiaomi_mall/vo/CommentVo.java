package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private int commentId;
    //商品id
    private int productId;

    private Long userId;

    private List<CommentVo> children;
    //根评论id
    private int parentId;
    //评论等级
    private String rate;
    //评论人名字
    private String nickName;

    private String toCommentNickName;
    //评论内容
    private String content;

    private Long toCommentUserId;

    //回复目标评论id
    private int toCommentId;

    private Date commentTime;
}
