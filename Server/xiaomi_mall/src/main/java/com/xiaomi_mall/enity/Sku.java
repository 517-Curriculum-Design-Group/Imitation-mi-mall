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
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("商品规格实体")
@TableName("SKU")
public class Sku implements Serializable {

    @ApiModelProperty("商品规格id(主键)")
    @TableId(type = IdType.AUTO)
    private Integer skuId;

    @ApiModelProperty("商品id(外键)")
    private Integer productId;

    @ApiModelProperty("商品规格名字")
    private String skuName;

    @ApiModelProperty("商品规格图片")
    private String skuImage;

    @ApiModelProperty("商品规格价格")
    private BigDecimal skuPrice;

    @ApiModelProperty("商品规格库存")
    private Integer skuStock;

    @ApiModelProperty("商品规格创建时间")
    private Date createTime;

    @ApiModelProperty("逻辑删除符")
    private int delFlag;

}
