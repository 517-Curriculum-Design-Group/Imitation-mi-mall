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
@TableName("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("用户id(主键)")
    private long userId;

    @ApiModelProperty("收件人名字")
    private Integer productId;

    @ApiModelProperty("商品数量")
    private Integer quantity;


}
