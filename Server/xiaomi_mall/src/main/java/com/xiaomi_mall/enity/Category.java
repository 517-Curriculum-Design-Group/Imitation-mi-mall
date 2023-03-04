package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分类实体")
@TableName("Category")
public class Category implements Serializable {

    @ApiModelProperty("主键")
    private int categoryId;

    @ApiModelProperty("外键？")
    private int parentId;

    @ApiModelProperty("分类名称")
    private String categoryName;



}
