package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.SkuAttributeValue;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SkuAttributeValueMapper extends BaseMapper<SkuAttributeValue> {

    @Insert("INSERT INTO SkuAttributeValue (attribute_id, value_name) VALUES (#{attribute_id}, #{value_name})")
    Integer addSkuValue(Integer attribute_id, String value_name);

    @Select("SELECT * FROM SkuAttributeValue WHERE attribute_id = #{attribute_id} AND del_flag = '0'")
    List<SkuAttributeValue> getAllByAttributeId(Integer attribute_id);

    @Select("SELECT value_name FROM SkuAttributeValue WHERE attribute_id = #{attribute_id}")
    List<String> skuValueList(Integer attribute_id);

    @Update("UPDATE SkuAttributeValue SET del_flag = #{del_flag} WHERE value_id = #{value_id}")
    int modifyAttributeValueStatus(int value_id, int del_flag);

}
