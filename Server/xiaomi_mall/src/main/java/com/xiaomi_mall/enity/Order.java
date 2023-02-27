package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel("订单实体")
@TableName("order")
public class Order implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("用户id")
    private long userId;

    @ApiModelProperty("地址id")
    private long addressId;

    @ApiModelProperty("订单的价格")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单生成时间")
    private LocalDateTime createTime;

    @ApiModelProperty("订单状态")
    private Integer status;

}
