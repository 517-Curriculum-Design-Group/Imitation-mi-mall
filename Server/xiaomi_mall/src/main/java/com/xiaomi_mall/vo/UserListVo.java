package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListVo {
    /**
     * 主键
     */
    private Long userId;

    private int status;

    private String nickName;

    private String mobile;

    private Date createTime;
}
