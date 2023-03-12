package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel("商品规格属性的值实体")
@TableName("SkuAttributeValue")
public class SkuAttributeValue implements Serializable {

    @ApiModelProperty("id(主键)")
    @TableId(type = IdType.AUTO)
    private Integer valueId;

    @ApiModelProperty("属性id(外键)")
    private Integer attributeId;

    @ApiModelProperty("key的value值")
    private String valueName;
}
