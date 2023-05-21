package com.xiaomi_mall.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductListVo {

    private int productId;

    private int categoryId;

    private String categoryName;

    private String productName;

    private String productPic;

    private String leastPrice;

    private String productDescription;

    private Date createTime;
}
