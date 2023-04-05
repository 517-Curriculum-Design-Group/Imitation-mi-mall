package com.xiaomi_mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaomi_mall.enity.SkuAttribute;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SkuAttributeMapper extends BaseMapper<SkuAttribute> {

    @Insert("insert into SkuAttribute (attribute_name) VALUES (#{attribute_name})")
    Boolean addSkuName(String attribute_name);

    @Select("SELECT attribute_id FROM SkuAttribute WHERE attribute_name = #{attribute_name} AND del_flag = '0'")
    List<Integer> getIdByAttributeName(String attribute_name);

    @Select("SELECT * FROM SkuAttribute WHERE attribute_id = #{attribute_id}")
    List<SkuAttribute> getAllByAttributeId(int attribute_id);

    @Select("SELECT * FROM SkuAttribute WHERE del_flag = '0'")
    List<SkuAttribute> getAll();

    @Update("UPDATE SkuAttribute SET attribute_name = #{attribute_name} WHERE attribute_id = #{attribute_id}")
    int modifySkuName(int attribute_id, String attribute_name);

    @Update("UPDATE SkuAttribute SET status = #{status} WHERE attribute_id = #{attribute_id}")
    int modifyAttributeStatus(int attribute_id, int status);


}
