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
@ApiModel("商品属性实体")
@TableName("attributes")
public class Attributes implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("属性id(主键)")
    private Integer attributeId;

    @ApiModelProperty("属性名称")
    private Integer attributeName;

    @ApiModelProperty("属性值")
    private Integer attributeValue;
}
