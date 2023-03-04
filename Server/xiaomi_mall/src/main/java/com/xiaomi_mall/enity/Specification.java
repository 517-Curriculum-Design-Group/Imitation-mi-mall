package com.xiaomi_mall.enity;

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
@ApiModel("商品规格实体")
@TableName("specification")
public class Specification implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("商品id(主键)")
    private Integer productId;

    @ApiModelProperty("属性id(主键)")
    private Integer attributesId;

    @ApiModelProperty("属性值")
    private String value;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("库存")
    private Integer stock;

}
