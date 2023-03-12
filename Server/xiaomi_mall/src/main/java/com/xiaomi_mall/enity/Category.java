package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分类实体")
@TableName("Category")
public class Category implements Serializable {

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private int categoryId;

    @ApiModelProperty("外键？")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @TableField(exist = false)
    private String productName;
    @TableField(exist = false)
    private int productId;
    @TableField(exist = false)
    private String productDescription;
    @TableField(exist = false)
    private String image;
    @TableField(exist = false)
    private BigDecimal skuPrice;

}
