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
@ApiModel("订单实体")
@TableName("Order")
public class Order implements Serializable {

    @ApiModelProperty("用户id(主键)")
    @TableId(type = IdType.AUTO)
    private int orderId;

    @ApiModelProperty("用户id(外键)")
    private Long userId;

    @ApiModelProperty("地址id(外键)")
    private int addressId;

    @ApiModelProperty("订单的价格")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单生成时间")
    @TableField(fill = FieldFill.INSERT)
    private Date orderTime;

    @ApiModelProperty("订单状态")
    private int status;

}
