package com.xiaomi_mall.enity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("收藏实体")
@TableName("Favorite")
public class Favorite implements Serializable {

    @ApiModelProperty("收藏id(主键)")
    private int favoriteId;

    @ApiModelProperty("用户id(外键)")
    private int userId;

    @ApiModelProperty("商品id(外键)")
    private int productId;

    @ApiModelProperty("收藏生成时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime favoriteTime;
}
