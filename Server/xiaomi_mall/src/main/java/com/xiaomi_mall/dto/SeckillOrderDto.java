package com.xiaomi_mall.dto;

import com.xiaomi_mall.enity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillOrderDto {

    private String productName;

    private Order order;

    private int skuId;
}
