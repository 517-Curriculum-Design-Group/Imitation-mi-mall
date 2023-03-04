package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("购物车实体")
@TableName("Cart")
public class Cart implements Serializable {

    @ApiModelProperty("购物车id(主键)")
    private int cartId;

    @ApiModelProperty("用户id(外键)")
    private int userId;

    @ApiModelProperty("选中商品的该规格id(外键)")
    private int skuId;

    @ApiModelProperty("选中商品的该规格名字")
    private String skuName;

    @ApiModelProperty("选中商品的该规格对应图片")
    private String skuImage;

    @ApiModelProperty("选中商品的该规格对应的价格")
    private String skuPrice;

    @ApiModelProperty("选中商品的该规格对应的数量")
    private String skuQuantity;


}