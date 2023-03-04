package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("地址实体")
@TableName("Address")
public class Address implements Serializable {

    @ApiModelProperty("地址id(主键)")
    private int addressId;

    @ApiModelProperty("用户id(外键)")
    private int userId;

    @ApiModelProperty("收件人名字")
    private String recipientName;

    @ApiModelProperty("收件人电话号码")
    private String recipientPhone;

    //以下为地址（省-市-县-详细
    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("县")
    private String district;

    @ApiModelProperty("详细")
    private String detail;

    @ApiModelProperty("地址是否默认标识")
    private int isDefault;
}
