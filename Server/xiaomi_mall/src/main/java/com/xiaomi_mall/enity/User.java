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
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体")
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("账号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phonenumber;

    @ApiModelProperty("用户性别（0男，1女，2未知）")
    private String sex;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户类型")
    private String userType;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建人的用户id")
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新人")
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;
}