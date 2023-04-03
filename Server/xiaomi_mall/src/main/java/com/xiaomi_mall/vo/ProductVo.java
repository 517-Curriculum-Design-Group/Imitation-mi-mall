package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    private int productId;
    private int categoryId;
    private String productName;
    private String productPic;
    private Map<String, Object> skuList = new LinkedHashMap<>();
    private String productDescription;
    private Date createTime;
}
