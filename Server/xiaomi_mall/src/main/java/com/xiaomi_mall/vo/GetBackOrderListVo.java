package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBackOrderListVo
{
    long total;
    List<BackOrderListVo> productListVos;
}
