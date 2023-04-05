package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuVo {

    private Map<String, String> skuName = new LinkedHashMap<>();
    private BigDecimal skuPrice;
    private Integer skuStock;
}
