package com.xiaomi_mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyAddressInOrderDto {
    int orderId;
    private String newName;
    private String newPhone;
    String newAddress;
}
