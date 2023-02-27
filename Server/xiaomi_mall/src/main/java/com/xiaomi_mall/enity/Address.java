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
@ApiModel("地址实体")
@TableName("address")
public class Address implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("地址id(主键)")
    private Integer addressId;

    @ApiModelProperty("用户id")
    private long userId;

    @ApiModelProperty("收件人名字")
    private String name;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("地址")
    private String address;
}
