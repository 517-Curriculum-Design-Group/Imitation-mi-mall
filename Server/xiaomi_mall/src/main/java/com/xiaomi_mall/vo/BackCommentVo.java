package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BackCommentVo {

    private int commentId;

    private int productId;

    private String productName;

    private String productPic;

    private String content;

    private String rate;

    private Date commentTime;


}
