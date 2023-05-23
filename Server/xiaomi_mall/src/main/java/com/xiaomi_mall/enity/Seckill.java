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
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("秒杀实体")
@TableName("Seckill")
public class Seckill implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer seckillId;

    @ApiModelProperty("商品id")
    private Integer productId;

    @ApiModelProperty("skuid")
    private Integer skuId;

    @ApiModelProperty("商品价格")
    private BigDecimal seckillPrice;

    @ApiModelProperty("库存")
    private Integer stockCount;

    @ApiModelProperty("秒杀开始时间")
    private Date startTime;

    @ApiModelProperty("秒杀结束时间")
    private Date endTime;

    @ApiModelProperty("逻辑删除符")
    private int delFlag;
}
