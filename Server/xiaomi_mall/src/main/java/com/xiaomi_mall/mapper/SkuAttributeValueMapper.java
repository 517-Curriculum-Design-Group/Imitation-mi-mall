package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.SkuAttributeValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkuAttributeValueMapper extends BaseMapper<SkuAttributeValue> {

    @Select("SELECT value_name FROM SkuAttributeValue WHERE attribute_id = #{attribute_id}")
    List<String> skuValueList(Integer attribute_id);
}
