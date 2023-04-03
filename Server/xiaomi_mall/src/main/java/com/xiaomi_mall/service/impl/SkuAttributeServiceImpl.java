package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.enity.SkuAttribute;
import com.xiaomi_mall.enity.SkuAttributeValue;
import com.xiaomi_mall.mapper.SkuAttributeMapper;
import com.xiaomi_mall.mapper.SkuAttributeValueMapper;
import com.xiaomi_mall.service.SkuAttributeService;
import com.xiaomi_mall.service.SkuAttributeValueService;
import com.xiaomi_mall.vo.SkuValueDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkuAttributeServiceImpl extends ServiceImpl<SkuAttributeMapper, SkuAttribute> implements SkuAttributeService {
    @Autowired
    private SkuAttributeMapper skuAttributeMapper;
    @Autowired
    @Lazy
    private SkuAttributeService skuAttributeService;
    @Autowired
    private SkuAttributeValueService skuAttributeValueService;
    @Autowired
    private SkuAttributeValueMapper skuAttributeValueMapper;

    @Override
    public Result getSkuList(Integer pageNum, Integer pageSize) {
        //获取规格表的所有数据
        List<SkuAttribute> skuAttributeList = skuAttributeService.list();
        //获取规格对应的值表的所有数据
        List<SkuAttributeValue> skuAttributeValueList = skuAttributeValueService.list();
        //建立一个哈希表
        HashMap<String, List<String>> map = new HashMap<>();

        //循环访问
        for (int i = 0; i < skuAttributeList.size(); i++) {
            String key = skuAttributeList.get(i).getAttributeName();
            Integer id = skuAttributeList.get(i).getAttributeId();
            List<String> res = new ArrayList<>();
            map.put(key, res);
            for (int j = 0; j < skuAttributeValueList.size(); j++) {
                Integer subId = skuAttributeValueList.get(j).getAttributeId();
                String valueName = skuAttributeValueList.get(j).getValueName();
                if (Objects.equals(subId, id)) {
                    res.add(valueName);
                }
            }
        }
        return Result.okResult(map);
    }

    @Override
    public Result getSkuDetail(Integer attributeId) {
        SkuAttribute skuAttribute = skuAttributeMapper.selectById(attributeId);
        String attributeName = skuAttribute.getAttributeName();
        List<String> skuValueList = skuAttributeValueMapper.skuValueList(attributeId);

        SkuValueDetailVo skuValueDetailVo = new SkuValueDetailVo();
        skuValueDetailVo.setAttributeName(attributeName);
        skuValueDetailVo.setSkuValueList(skuValueList);

        return Result.okResult(skuValueDetailVo);
    }

}
