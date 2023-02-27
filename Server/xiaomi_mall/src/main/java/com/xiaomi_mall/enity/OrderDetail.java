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
@ApiModel("订单详情实体")
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("主键")
    private Integer orderDetailsId;

    @ApiModelProperty("订单id")
    private Integer orderId;

    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("商品数量")
    private Integer quantity;

    @ApiModelProperty("订单的价格")
    private BigDecimal price;


}
