package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ApiModel("评论实体")
@TableName("Comment")



public class Comment implements Serializable {

    @ApiModelProperty("评论id(主键)")
    @TableId(type = IdType.AUTO)
    private int commentId;

    @ApiModelProperty("用户id(外键)")
    private Long userId;

    @ApiModelProperty("商品id(外键)")
    private int productId;

    @ApiModelProperty("评论内容")
    private String content;


    @ApiModelProperty("评价等级")
    private String rate;

    private Long toCommentUserId;

    private int toCommentId;

    @ApiModelProperty("评价时间")
    @TableField(fill = FieldFill.INSERT)
    private Date commentTime;

    @ApiModelProperty("父评论ID")
    private int parentId;

    @ApiModelProperty("逻辑删除符")
    private int delFlag;
}
