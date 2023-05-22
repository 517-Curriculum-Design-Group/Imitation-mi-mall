package com.xiaomi_mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BackOrderListVo {
    private int orderId;
    private String userName;
    private BigDecimal totalPrice;
    private Date orderTime;
    private int status;
    private String address;
    private String name;
    private String phone;
}
