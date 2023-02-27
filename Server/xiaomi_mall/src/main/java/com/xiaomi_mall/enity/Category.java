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
@TableName("category")
public class Category implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("分类名称")
    private String categoryName;

}
