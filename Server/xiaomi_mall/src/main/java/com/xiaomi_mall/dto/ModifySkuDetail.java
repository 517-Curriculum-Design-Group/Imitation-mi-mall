package com.xiaomi_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifySkuDetail {
    public List<String> skuNames;
    public List<String> skuValues;
    public double skuPrice;
    public int skuStock;
}
