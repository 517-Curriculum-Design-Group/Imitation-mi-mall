package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private int productId;
    private int categoryId;
    private String productName;
    private String productPic;
    private List<Map<String, Object>> skuList;
    private List<SkuVo> skuVoList;
    private String productDescription;
    private Date createTime;
}
