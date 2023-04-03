package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.SkuAttribute;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuAttributeMapper extends BaseMapper<SkuAttribute> {

    @Insert("insert into SkuAttribute VALUES (#{attribute_name})")
    Boolean addSkuName(String attribute_name);
}
