package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("商品实体")
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("主键")
    private Integer productId;

    @ApiModelProperty("商品名字")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("分类名字")
    private String category_name;

    @ApiModelProperty("商品价格")
    private BigDecimal price;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("商品状态")
    private Integer status;
}
