package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.AttributeValueCommit;
import com.xiaomi_mall.enity.SkuAttribute;
import com.xiaomi_mall.enity.SkuAttributeValue;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.mapper.SkuAttributeValueMapper;
import com.xiaomi_mall.service.SkuAttributeService;
import com.xiaomi_mall.service.SkuAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkuAttributeValueServiceImpl extends ServiceImpl<SkuAttributeValueMapper, SkuAttributeValue> implements SkuAttributeValueService {

    @Autowired
    @Lazy
    private SkuAttributeService skuAttributeService;
    @Autowired
    @Lazy
    private SkuAttributeValueService skuAttributeValueService;



    @Override
    public Result createNewAttributeValue(AttributeValueCommit attributeValueCommit) {
        List<SkuAttribute> skuAttributeList = skuAttributeService.list();
        boolean find = false;
        for (int i = 0; i < skuAttributeList.size(); i++) {
            if(skuAttributeList.get(i).getAttributeId() == attributeValueCommit.getAttributeId())
            {
                find = true;
                break;
            }
        }
        if(find == false) return Result.errorResult(AppHttpCodeEnum.ATTRIBUTEID_NOT_EXIST);

        List<SkuAttributeValue> skuAttributeValueList = skuAttributeValueService.list();
        for (int i = 0; i < skuAttributeValueList.size(); i++)
            if(skuAttributeValueList.get(i).getValueName().equals(attributeValueCommit.getAttributeValueName()))
                return Result.errorResult(AppHttpCodeEnum.ATTRIBUTEVALUE_IS_EXIST);

        SkuAttributeValue skuAttributeValue = new SkuAttributeValue();
        skuAttributeValue.setAttributeId(attributeValueCommit.getAttributeId());
        skuAttributeValue.setValueName(attributeValueCommit.getAttributeValueName());
        skuAttributeValue.setDelFlag(0);
        skuAttributeValueService.save(skuAttributeValue);

        return Result.okResult("新增成功!");
    }
}
