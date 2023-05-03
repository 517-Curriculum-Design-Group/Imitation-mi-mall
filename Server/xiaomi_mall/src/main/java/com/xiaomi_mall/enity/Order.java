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
@TableName("`Order`")
public class Order implements Serializable {

    @ApiModelProperty("订单id(主键)")
    @TableId(type = IdType.AUTO)
    private int orderId;

    @ApiModelProperty("用户id(外键)")
    private Long userId;

    @ApiModelProperty("订单的价格")
    private BigDecimal totalPrice;

    @ApiModelProperty("订单生成时间")
    @TableField(fill = FieldFill.INSERT)
    private Date orderTime;

    @ApiModelProperty("订单状态")
    private int status;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("收货人姓名")
    private String name;

    @ApiModelProperty("收货人电话")
    private String phone;

    @ApiModelProperty("逻辑删除符")
    private int delFlag;

}
