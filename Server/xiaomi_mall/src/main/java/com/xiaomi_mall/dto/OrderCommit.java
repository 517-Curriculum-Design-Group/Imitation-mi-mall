package com.xiaomi_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCommit
{
    private String productName;
    private int skuId;
    private int commitCount;
}