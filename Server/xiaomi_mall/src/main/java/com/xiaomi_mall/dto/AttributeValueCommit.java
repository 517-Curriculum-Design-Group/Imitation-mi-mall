package com.xiaomi_mall.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValueCommit {
    private int attributeId;
    private String attributeValueName;
}
