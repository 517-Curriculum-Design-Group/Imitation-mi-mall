package com.xiaomi_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifySkuDetailDto {

    private int productId;
    private List<ModifySku> skuList;
    private List<ModifySkuDetail> skuDetailList;
}

