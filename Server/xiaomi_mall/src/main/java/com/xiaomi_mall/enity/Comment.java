package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论实体")
@TableName("Comment")
public class Comment implements Serializable {

    @ApiModelProperty("评论id(主键)")
    private int commentId;

    @ApiModelProperty("用户id(外键)")
    private int userId;

    @ApiModelProperty("商品id(外键)")
    private int productId;

    @ApiModelProperty("评论内容")
    private String content;

    public enum CommentRate
    {
        好评, 一般, 差评
    }
    @ApiModelProperty("评价等级")
    private CommentRate rate;

    @ApiModelProperty("评价时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime commentTime;

    @ApiModelProperty("父评论ID")
    private String parentId;
}
