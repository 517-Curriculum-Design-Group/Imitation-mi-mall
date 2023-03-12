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
@ApiModel("商品属性实体")
@TableName("SkuAttribute")
public class SkuAttribute implements Serializable {

    @ApiModelProperty("属性id(主键)")
    @TableId(type = IdType.AUTO)
    private Integer attributeId;

    @ApiModelProperty("key名称")
    private String attributeName;

}
