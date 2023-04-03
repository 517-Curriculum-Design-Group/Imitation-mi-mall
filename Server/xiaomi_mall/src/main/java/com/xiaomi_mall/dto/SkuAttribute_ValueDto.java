package com.xiaomi_mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuAttribute_ValueDto {

    private int attributeId;

    private String attributename;

    private List<String> valueName;
}
