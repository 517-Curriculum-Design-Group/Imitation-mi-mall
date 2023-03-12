package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateProductVo {
    private int categoryId;
    private int productId;
    private String productName;
    private String productDescription;
    private String skuImage;
    private BigDecimal skuPrice;
}
