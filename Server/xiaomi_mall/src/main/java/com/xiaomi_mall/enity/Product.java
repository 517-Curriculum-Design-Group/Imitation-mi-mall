package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("商品实体")
@TableName("Product")
public class Product implements Serializable {

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private int productId;

    @ApiModelProperty("分类id")
    private int categoryId;

    @ApiModelProperty("商品名字")
    private String productName;

    @ApiModelProperty("商品图片")
    private String productPic;

    @ApiModelProperty("起售价")
    private String leastPrice;

    @ApiModelProperty("SKU列表")
    private String skuList;

    @ApiModelProperty("商品描述")
    private String productDescription;

    @ApiModelProperty("销售状态")
    private int status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("逻辑删除符")
    private int delFlag;


}
