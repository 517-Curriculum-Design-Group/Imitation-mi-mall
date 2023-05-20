package com.xiaomi_mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.*;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.mapper.*;
import com.xiaomi_mall.service.CartService;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.util.JwtUtil;
import com.xiaomi_mall.vo.ProductListVo;
import com.xiaomi_mall.vo.ProductVo;
import com.xiaomi_mall.vo.SkuVo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SkuAttributeMapper skuAttributeMapper;
    @Autowired
    private SkuAttributeValueMapper skuAttributeValueMapper;

    @Override
    public Result getProductDetail(Integer product_id) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getProductId, product_id);
        Product product = getOne(queryWrapper);

        ProductVo productVo = new ProductVo();
        productVo.setProductId(product.getProductId());
        productVo.setCategoryId(product.getCategoryId());
        productVo.setProductName(product.getProductName());
        productVo.setProductPic(product.getProductPic());
        Map<String, Object> map = JSON.parseObject(product.getSkuList(), LinkedHashMap.class, Feature.OrderedField);
        List<Map<String, Object>> mapReses = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            Map<String, Object> mapRes = new LinkedHashMap<>();
            String mapKey = entry.getKey();
            mapRes.put("attributeName", mapKey);
            List<String> values = (List<String>) entry.getValue();
            mapRes.put("attributeValues", values);
            mapReses.add(mapRes);
        }
        productVo.setSkuList(mapReses);

        List<Sku> skuList = skuMapper.getSkuListByProductId(product_id);
        List<SkuVo> skuVoList = new ArrayList<>();
        for (Sku sku:skuList)
        {
            List<String> skus = new ArrayList<>();
            Map<String, Object> map1 = JSON.parseObject(sku.getSkuName(), LinkedHashMap.class, Feature.OrderedField);
            for (Map.Entry<String, Object> entry : map1.entrySet())
            {
                String value = (String) entry.getValue();
                skus.add(value);
            }
            skuVoList.add(new SkuVo(sku.getSkuId(), skus, sku.getSkuPrice(), sku.getSkuStock()));
        }

        productVo.setSkuVoList(skuVoList);
        productVo.setProductDescription(product.getProductDescription());

        return Result.okResult(productVo);
    }

    @Override
    public Result getProductList(Integer pageNum, Integer pageSize, String productName) {
        //构造器
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //判断前端是否传入productName字段
        queryWrapper.like(Objects.nonNull(productName), Product::getProductName, productName);
        //分页
        Page<Product> pageInfo = new Page<>(pageNum, pageSize);
        //分页查询
        page(pageInfo, queryWrapper);
        //获取到Product的数据
        List<Product> productList = pageInfo.getRecords();
        //封装到vo类
        List<ProductListVo> productListVos = toProductListVo(productList);

        return Result.okResult(productListVos);

    }

    @Override
    public Result deleteCategory(Integer categoryId) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        Long parentId = categoryService.getById(categoryId).getParentId();
        if (parentId == SystemConstants.ROOT_ID) {
            int size = categoryMapper.getChildrenCate(categoryId).size();
            if (size > SystemConstants.CATE_CAN_DELETE) {
                throw new SystemException(AppHttpCodeEnum.CHILDREN_CATEGORY_EXIST);
            }
            return Result.okResult();
        }
        else {
            queryWrapper.eq(Product::getCategoryId, categoryId);
            int count = count(queryWrapper);
            if (count > SystemConstants.CATE_CAN_DELETE) {
                throw new SystemException(AppHttpCodeEnum.PRODUCT_EXIST);
            }
            return Result.okResult();
        }

    }

    @Override
    public Result addNewProduct(Map<String, Object> map)
    {
        Map<String, List<String>> skuList = (Map<String, List<String>>)map.get("skuName");
        String skuListJson = null;
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            skuListJson = objectMapper.writeValueAsString(skuList);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return Result.errorResult(801, "Json转String失败");
        }
        System.out.println(skuListJson);

        Product product = new Product();

        product.setCategoryId(Integer.parseInt(map.get("categoryId").toString()));
        product.setProductName(map.get("productName").toString());
        product.setProductPic(map.get("productPic").toString());
        product.setSkuList(skuListJson);
        product.setProductDescription(map.get("productDescription").toString());
        save(product);
        return Result.okResult(200, "添加商品成功");
    }

    @Override
    public Result getProductSku(Integer productId)
    {
        List<Sku> skuList = skuMapper.getSkuListByProductId(productId);
        List<SkuVo> skuVoList = new ArrayList<>();

        for (Sku sku:skuList)
        {
            List<String> skus = new ArrayList<>();
            Map<String, Object> map = JSON.parseObject(sku.getSkuName(), LinkedHashMap.class, Feature.OrderedField);
            for (Map.Entry<String, Object> entry : map.entrySet())
            {
                skus.add((String) entry.getValue());
            }
            skuVoList.add(new SkuVo(sku.getSkuId(), skus, sku.getSkuPrice(), sku.getSkuStock()));
        }

        return Result.okResult(skuVoList);
    }


    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public Result modifyProductSku(Map<String, Object> map)
    {
        int productId = Integer.parseInt(map.get("productId").toString());
        List<Integer> attributeIdList = (List<Integer>)map.get("attributeIdList");
        List<List<Integer>> valueIdList = (List<List<Integer>>)map.get("valueIdList");
        Map<String, List<String>> skuList = (Map<String, List<String>>)map.get("skuList");
        List<Map<String, Object>> skuDetailList = (List<Map<String, Object>>)map.get("skuDetailList");

        if(attributeIdList.size() < 1)
            return Result.errorResult(802,"attributeIdList不能为空，一个商品至少要一个SKU");

        if(valueIdList.size() < 1)
            return Result.errorResult(802,"valueIdList不能为空，一个商品至少要一个SKU");

        if(skuList.size() < 1)
            return Result.errorResult(802,"skuList不能为空，一个商品至少要一个SKU");

        if(skuDetailList.size() < 1)
            return Result.errorResult(802,"skuDetailList不能为空，一个商品至少要一个SKU");

        //对属性判断是否可操作
        List<SkuAttribute> attributeList = skuAttributeMapper.getAll();
        for (int id: attributeIdList)
        {
            boolean remain = false;
            for (SkuAttribute attribute: attributeList)
            {
                if(attribute.getAttributeId() == id)
                {
                    remain = true;
                    break;
                }
            }
            if(!remain)
                return Result.errorResult(804, "id为" + id +"的attribute不存在");
        }

        //对属性值判断是否可操作
        List<SkuAttributeValue> attributeValueList = skuAttributeValueMapper.getAll();
        for (int i = 0 ; i < valueIdList.size() ; i++)
        {
            for (int j = 0; j < valueIdList.get(i).size() ; j++)
            {
                int target = valueIdList.get(i).get(j);
                List<SkuAttributeValue> res = attributeValueList.stream().filter(v->v.getValueId() == target).collect(Collectors.toList());
                if(res.isEmpty())
                    return Result.errorResult(805, "id为"+target+"的attributeValue不存在");
            }
        }

        //json转字符串
        String skuListJson = JsonToString(skuList);
        if(skuListJson == null)
        {
            return Result.errorResult(801, "Json转String失败，请检查skuList格式");
        }

        //覆盖Product表中的旧SkuList
        int row = productMapper.modifySkuList(productId, skuListJson);
        if(row == 0)
            return Result.errorResult(806, "覆盖Product表中的旧SkuList失败，没有任意一行被修改");

        //覆盖Product表中的旧least_price
        double leastPrice = 0;
        for(Map<String, Object> skuDetail : skuDetailList)
        {
            BigDecimal price = (BigDecimal)skuDetail.get("skuPrice");
            leastPrice = Math.min(leastPrice, price.doubleValue());
        }
        if(leastPrice != 0)
            productMapper.modifySkuList(productId, leastPrice + "元起");
        else
            productMapper.modifySkuList(productId, "");

        //修改/新增SKU，SKU即售卖的最小单元
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        SkuMapper skuMapperInSession = sqlSession.getMapper(SkuMapper.class);

        List<Sku> oldSkuDetailList =  skuMapper.getSkuListByProductId(productId);
        List<Integer> oldRemainSkuId = new ArrayList<>();

        if(!oldSkuDetailList.isEmpty())
        {
            List<Sku> modifySkuList = new ArrayList();

            for(int i = 0; i < skuDetailList.size() ; i++)
            {
                String skuName = JsonToString(skuDetailList.get(i).get("skuName"));
                if(skuName == null)
                {
                    return Result.errorResult(801, "Json转String失败，请检查skuList格式");
                }

                for(Sku oldSkuDetail : oldSkuDetailList)
                {
                    System.out.println("old" + oldSkuDetail.getSkuName());
                    System.out.println("new" + skuName);
                    if(oldSkuDetail.getSkuName().equals(skuName))
                    {
                        Sku modifySku = new Sku();
                        modifySku.setSkuId(oldSkuDetail.getSkuId());
                        modifySku.setProductId(productId);
                        modifySku.setSkuName(skuName);
                        modifySku.setSkuPrice(new BigDecimal(skuDetailList.get(i).get("skuPrice").toString()));
                        modifySku.setSkuStock(Integer.parseInt(skuDetailList.get(i).get("skuStock").toString()));
                        modifySku.setSkuImage(null);
                        modifySku.setCreateTime(new Date());

                        modifySkuList.add(modifySku);
                        oldRemainSkuId.add(i);
                        oldSkuDetailList.remove(oldSkuDetail);
                        break;
                    }
                }

                //覆盖要被保留的旧值
                if(!modifySkuList.isEmpty())
                {
                    //System.out.println("覆盖要被保留的旧值");
                    modifySkuList.stream().forEach(modifySku -> skuMapper.updateById(modifySku));
                    sqlSession.commit();
                    sqlSession.clearCache();
                }
            }

            List<Integer> deleteSkuList = new ArrayList();
            for(Sku oldSkuDetail : oldSkuDetailList)
                deleteSkuList.add(oldSkuDetail.getSkuId());

            //删除不需要的旧值
            if(!deleteSkuList.isEmpty())
            {
                //System.out.println("删除不需要的旧值");
                deleteSkuList.stream().forEach(sku_id -> skuMapperInSession.modifySkuStatus(sku_id, 1));
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }

        //插入新值
        List<Sku> newSkuDetailList = new ArrayList<>();
        for (int i = 0; i < skuDetailList.size(); i++)
        {
            if(!oldRemainSkuId.contains(i))
            {
                String skuName = JsonToString(skuDetailList.get(i).get("skuName"));
                if(skuName == null)
                {
                    return Result.errorResult(801, "Json转String失败，请检查skuList格式");
                }

                Sku newSku = new Sku();
                newSku.setProductId(productId);
                newSku.setSkuName(skuName);
                newSku.setSkuImage(null);
                newSku.setSkuPrice(new BigDecimal(skuDetailList.get(i).get("skuPrice").toString()));
                newSku.setSkuStock(Integer.parseInt(skuDetailList.get(i).get("skuStock").toString()));
                newSku.setCreateTime(new Date());

                newSkuDetailList.add(newSku);
            }
        }

        if(!newSkuDetailList.isEmpty())
        {
            //System.out.println("插入新值");
            newSkuDetailList.forEach(skuMapperInSession::insert);
            sqlSession.commit();
            sqlSession.clearCache();
        }

        return Result.okResult(200, "修改Sku成功");
    }

    private String JsonToString(Object json)
    {
        String res = null;
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            res = objectMapper.writeValueAsString(json);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            System.out.println("JsonToString出错");
            return null;
        }
        System.out.println(res);
        return res;
    }



    private List<ProductListVo> toProductListVo(List<Product> list) {
        List<ProductListVo> productListVos = BeanCopyUtils.copyBeanList(list, ProductListVo.class);
        //查询productListVos
        for (ProductListVo vo : productListVos) {
            String categoryName = categoryService.getById(vo.getCategoryId()).getCategoryName();
            vo.setCategoryName(categoryName);
        }
        return productListVos;
    }


    @Override
    public Result addSkuToCart(HttpServletRequest request, Integer sku_id)
    {
        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Sku sku = skuMapper.selectById(sku_id);
        Product product = productMapper.selectById(sku.getProductId());

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductName(product.getProductName());
        cart.setSkuId(sku_id);
        cart.setSkuName(sku.getSkuName());
        cart.setSkuImage(sku.getSkuImage());
        cart.setSkuPrice(sku.getSkuPrice());
        cart.setSkuQuantity(1);
        cartService.save(cart);

        return Result.okResult("加入购物车成功");
    }




}
