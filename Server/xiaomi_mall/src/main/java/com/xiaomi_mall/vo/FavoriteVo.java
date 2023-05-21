package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteVo {

    private int productId;
    private int categoryId;
    private String productName;
    private String productPic;
    private List<Map<String, Object>> skuList;
    private List<SkuVo> skuVoList;
    private String productDescription;


}
