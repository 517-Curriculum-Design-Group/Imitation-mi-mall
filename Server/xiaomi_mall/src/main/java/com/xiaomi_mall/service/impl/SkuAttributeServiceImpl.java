package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.SkuAttribute_ValueDto;
import com.xiaomi_mall.enity.SkuAttribute;
import com.xiaomi_mall.enity.SkuAttributeValue;
import com.xiaomi_mall.mapper.SkuAttributeMapper;
import com.xiaomi_mall.mapper.SkuAttributeValueMapper;
import com.xiaomi_mall.mapper.SkuAttributeValueRelationMapper;
import com.xiaomi_mall.service.SkuAttributeService;
import com.xiaomi_mall.service.SkuAttributeValueService;
import com.xiaomi_mall.vo.SkuValueDetailVo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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

    @Autowired
    private SkuAttributeValueRelationMapper skuAttributeValueRelationMapper;


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


    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public Result addSkuAttribute(SkuAttribute_ValueDto skuAttribute_valueDto) {

        //新增一个规格要操作俩张表
        List<Integer> attribute_id_list = skuAttributeMapper.getIdByAttributeName(skuAttribute_valueDto.getAttributename());
        if(attribute_id_list != null)
        {
            return Result.errorResult(200, "已有规格名");
        }
        else
        {
            skuAttributeMapper.addSkuName(skuAttribute_valueDto.getAttributename());
            attribute_id_list = skuAttributeMapper.getIdByAttributeName(skuAttribute_valueDto.getAttributename());
        }

        int attribute_id = attribute_id_list.get(0);
        //使用批处理
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        SkuAttributeValueMapper skuAttributeValueMapperInSession = sqlSession.getMapper(SkuAttributeValueMapper.class);
        skuAttribute_valueDto.getValueName().stream().forEach(value -> skuAttributeValueMapperInSession.addSkuValue(attribute_id, value));
        sqlSession.commit();
        sqlSession.clearCache();

//性能极差的写法，不妨进行批处理
//        for (String value: skuAttribute_valueDto.getValueName())
//        {
//            skuAttributeValueMapper.addSkuValue(attributeId, value);
//        }

        Map<String, Integer> data = new HashMap<>();
        data.put("AttributeId", attribute_id);
        return Result.okResult(data);
    }

    @Override
    public Result modifySkuAttribute(SkuAttribute_ValueDto skuAttribute_valueDto)
    {
        int attribute_id = skuAttribute_valueDto.getAttributeId();
        String new_attribute = skuAttribute_valueDto.getAttributename();
        List<String> new_values_name = skuAttribute_valueDto.getValueName();

        List<SkuAttribute> attribute_list = skuAttributeMapper.getAllByAttributeId(attribute_id);
        if(attribute_list == null || attribute_list.isEmpty())
            return Result.errorResult(700, "无该规格");

        if(attribute_list.get(0).getStatus() == 1)
            return Result.errorResult(800, "该规格已被删除");

        if(skuAttribute_valueDto.getAttributename() == null && skuAttribute_valueDto.getAttributename().equals(""))
            return Result.errorResult(701, "传入字符串为空");


        //attribute_id对应的旧的value
        List<SkuAttributeValue> old_values = skuAttributeValueMapper.getAllByAttributeId(attribute_id);

        //即将删除的规格值
        List<Integer> delete_values_id = new ArrayList<>();
        for(SkuAttributeValue old_value : old_values)
        {
            boolean remain = false;
            for(String new_name : new_values_name)
            {
                if(old_value.getValueName().equals(new_name))
                {
                    new_values_name.remove(new_name);
                    remain = true;
                    break;
                }
            }
            if(!remain)
                delete_values_id.add(old_value.getValueId());
        }

        //检查即将删除的是否仍被引用
        List<Integer> relation_values_id = skuAttributeValueRelationMapper.getAllValueId();
        for (Integer delete_value_id :delete_values_id)
        {
            for (Integer relation_value_id:relation_values_id)
            {
                if(delete_value_id == relation_value_id)
                    return Result.errorResult(702, "旧规格值仍有商品引用");
            }
        }

        //操作skuAttribute表
        skuAttributeMapper.modifySkuName(attribute_id, new_attribute);
        
        //操作skuAttributeValue表
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        SkuAttributeValueMapper skuAttributeValueMapperInSession = sqlSession.getMapper(SkuAttributeValueMapper.class);
        //删除旧值
        if(!delete_values_id.isEmpty())
        {
            delete_values_id.stream().forEach(value_id -> skuAttributeValueMapperInSession.modifyAttributeValueStatus(value_id, 1));
            sqlSession.commit();
            sqlSession.clearCache();
        }
        //插入新值
        new_values_name.stream().forEach(value_name -> skuAttributeValueMapperInSession.addSkuValue(attribute_id, value_name));
        sqlSession.commit();
        sqlSession.clearCache();

        return Result.okResult(200, "修改成功");
    }

    //这是单个规格删除，批量删除不做
    @Override
    public Result deleteSkuAttribute(Integer attribute_id)
    {
        List<SkuAttribute> attribute_list = skuAttributeMapper.getAllByAttributeId(attribute_id);
        if(attribute_list.get(0).getStatus() == 1)
            return Result.errorResult(800, "该规格已被删除");

        skuAttributeMapper.modifyAttributeStatus(attribute_id, 1);
        return Result.okResult(200, "删除成功");
    }



}
