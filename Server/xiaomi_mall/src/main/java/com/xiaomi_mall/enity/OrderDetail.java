package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@ApiModel("订单详情实体")
@TableName("OrderDetail")
public class OrderDetail implements Serializable {

    @ApiModelProperty("订单详情(主键)")
    @TableId(type = IdType.AUTO)
    private int detailId;

    @ApiModelProperty("订单id(外键)")
    private int orderId;

    @ApiModelProperty("规格id(外键)")
    private int skuId;

    @ApiModelProperty("商品名")
    private String productName;

    @ApiModelProperty("选中商品的该规格名字")
    private String skuName;

    @ApiModelProperty("选中商品的该规格对应图片")
    private String skuImage;

    @ApiModelProperty("选中商品的该规格对应的价格")
    private BigDecimal skuPrice;

    @ApiModelProperty("选中商品的该规格对应的数量")
    private int skuQuantity;

    @ApiModelProperty("逻辑删除符")
    private int delFlag;
}
