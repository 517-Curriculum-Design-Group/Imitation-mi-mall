package com.xiaomi_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCateProductSortDto {
    private int categoryId;
    private int productId;
    private String productName;
    private String productDescription;
    private String productPic;
    private String leastPrice;
    private double price;
}
