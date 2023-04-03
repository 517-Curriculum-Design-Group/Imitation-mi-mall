package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.SkuAttributeValueRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SkuAttributeValueRelationMapper extends BaseMapper<SkuAttributeValueRelation> {



    @Select("SELECT value_id FROM SkuAttributeValueRelation WHERE del_flag = '0'")
    List<Integer> getAllValueId();
}
