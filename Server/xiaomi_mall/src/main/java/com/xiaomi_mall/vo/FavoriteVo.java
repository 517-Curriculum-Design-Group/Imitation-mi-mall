package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteVo {

    private int favoriteId;
    private int productId;
    private String productName;
    private String productPic;
    private String productDescription;

}
