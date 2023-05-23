package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillListVo {

    private Integer seckillId;

    private Integer productId;

    private String productName;

    private String productPic;

    private Integer skuId;

    private String skuName;

    private BigDecimal seckillPrice;

    private Integer stockCount;

    private Date startTime;

    private Date endTime;
}
