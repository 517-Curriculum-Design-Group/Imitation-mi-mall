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
@ApiModel("商品规格关系实体")
@TableName("SkuAttributeValueRelation")
public class SkuAttributeValueRelation implements Serializable {

    @ApiModelProperty("商品规格id(主键、外键)")
    private Integer skuId;

    @ApiModelProperty("valueid(主键、外键)")
    private Integer valueId;

}
