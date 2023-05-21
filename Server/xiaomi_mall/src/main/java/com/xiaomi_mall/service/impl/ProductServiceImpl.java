package com.xiaomi_mall.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.dto.*;
import com.xiaomi_mall.enity.*;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.mapper.*;
import com.xiaomi_mall.service.CartService;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.SkuService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.util.JwtUtil;
import com.xiaomi_mall.vo.ProductListVo;
import com.xiaomi_mall.vo.ProductVo;
import com.xiaomi_mall.vo.SkuVo;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
    private SkuService skuService;
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
        Product product = new Product();
        product.setCategoryId(Integer.parseInt(map.get("categoryId").toString()));
        product.setProductName(map.get("productName").toString());
        product.setProductPic(map.get("productPic").toString());
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
    public Result modifyProductSku(ModifySkuDetailDto modifySkuDetailDto){
        // region 传参格式
        //{
        //    "productId" : 12,
        //    "skuList":
        //    [
        //        {
        //            "skuName":"颜色",
        //            "skuValues":["白色","黑色"]
        //        },
        //        {
        //            "skuName":"尺寸",
        //            "skuValues":["14寸"]
        //        }
        //    ],
        //    "skuDetailList":
        //    [
        //        {
        //            "skuNames":["颜色","尺寸"],
        //            "skuValues":["白色","14寸"],
        //            "skuPrice":790,
        //            "skuStock":22
        //        },
        //        {
        //            "skuNames":["颜色","尺寸"],
        //            "skuValues":["黑色","14寸"],
        //            "skuPrice":790,
        //            "skuStock":12
        //        }
        //    ]
        //}
        //endregion

        //region 先覆盖Product中的旧的sku_list
        Map<String, Object> skuMapToJson = new LinkedHashMap<>();
        for (int i = 0; i < modifySkuDetailDto.getSkuList().size(); i++)
        {
            ModifySku modifySku = modifySkuDetailDto.getSkuList().get(i);
            skuMapToJson.put(modifySku.skuName, modifySku.skuValues);
        }
        String skuListJson = JSONUtils.toJSONString(skuMapToJson);
        //System.out.println(skuListJson);
        int row = productMapper.modifySkuList(modifySkuDetailDto.getProductId(), skuListJson);
        if(row == 0)
            return Result.errorResult(806, "覆盖Product表中的旧sku_list失败，没有任意一行被修改");
        //endregion

        //region 覆盖Product表中的旧least_price
        double leastPrice = Double.MAX_VALUE;
        for(ModifySkuDetail modifySkuDetail : modifySkuDetailDto.getSkuDetailList())
        {
            //BigDecimal price = new BigDecimal(modifySkuDetail.skuPrice);
            leastPrice = Math.min(leastPrice, modifySkuDetail.skuPrice);
        }
        if(leastPrice != 0)
            productMapper.modifyLeastPrice(modifySkuDetailDto.getProductId(), leastPrice + "元起");
        else
            productMapper.modifyLeastPrice(modifySkuDetailDto.getProductId(), "0元起");
        //endregion

        //region 删除sku表中旧的行
        QueryWrapper<Sku> skuQueryWrapper = new QueryWrapper<>();
        skuQueryWrapper
                .eq("product_id", modifySkuDetailDto.getProductId())
                .eq("del_flag", 0);
        List<Sku> deleteSkuList = skuMapper.selectList(skuQueryWrapper);
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        SkuMapper skuMapperInSession = sqlSession.getMapper(SkuMapper.class);
        deleteSkuList.stream().forEach(deleteSku -> skuMapperInSession.modifySkuStatus(deleteSku.getSkuId(), 1));
        sqlSession.commit();
        sqlSession.clearCache();
        //endregion

        //region 插入新的sku值
        String product_img = productMapper.selectById(modifySkuDetailDto.getProductId()).getProductPic();

        for (ModifySkuDetail skuDetail : modifySkuDetailDto.getSkuDetailList())
        {
            List<String> attributeNames =  skuDetail.getSkuNames();
            List<String> attributeValues = skuDetail.getSkuValues();
            List<Sku> skus = new ArrayList<>();
            Map<String, Object> skuJsonMap = new LinkedHashMap<>();
            for (int i = 0; i < attributeNames.size(); i++)
            {
                //数据库里就是这种结构转Json，这波属于是设计不足的代价
                skuJsonMap.put(attributeNames.get(i), attributeValues.get(i));
            }
            Sku sku = new Sku();
            sku.setProductId(modifySkuDetailDto.getProductId());
            String skuJson = JSONUtils.toJSONString(skuJsonMap);
            sku.setSkuName(skuJson);
            sku.setSkuImage(product_img);
            sku.setSkuPrice((new BigDecimal(skuDetail.getSkuPrice())));
            sku.setSkuStock(skuDetail.getSkuStock());
            sku.setCreateTime(new Date());
            skus.add(sku);
            skuService.saveBatch(skus);
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
    public Result addSkuToCart(HttpServletRequest request, Map<String, Object> map)
    {
        int sku_id = (int) map.get("sku_id");

        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id", userId)
                .eq("sku_id", sku_id)
                .eq("del_flag", 0);

        List<Cart> carts = cartMapper.selectList(cartQueryWrapper);
        if(!carts.isEmpty())
        {
            carts.get(0).setSkuQuantity(carts.get(0).getSkuQuantity() + 1);
            cartMapper.updateById(carts.get(0));
            return Result.okResult("加入购物车成功");
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

    @Override
    public Result getProductPrice(Map<String, Object> map) {
        List<String> attributeValues = (List<String>) map.get("attributeValues");
        int product_id = (int) map.get("product_id");
        Map<String, Object> res = new LinkedHashMap<>();

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

        for (int i = 0 ; i < attributeValues.size() ; i++)
        {
            for (SkuVo skuVo:skuVoList)
            {
                List<String> values = skuVo.getSkus();
                boolean find_flag = false;
                for (int j = 0 ; j < values.size() ; j++)
                {
                    if(!attributeValues.get(j).equals(values.get(j)))
                    {
                        find_flag = false;
                        break;
                    }
                    else
                    {
                        find_flag = true;
                    }
                }

                if(find_flag)
                {
                    res.put("price", skuVo.getSkuPrice());
                    res.put("stock", skuVo.getSkuStock());
                    res.put("sku_id", skuVo.getSkuId());
                    return Result.okResult(res);
                }
            }
        }

        return Result.errorResult(AppHttpCodeEnum.SKU_NOT_FIND);
    }

    @Override
    public Result addProductToFavorite(HttpServletRequest request, Integer product_id) {

        return Result.okResult("还没写完");
    }

    @Override
    public Result ModifyProductStatus(ModifyProductStatusDto modifyProductStatusDto)
    {
        if(modifyProductStatusDto.getStatus() != 0 || modifyProductStatusDto.getStatus() != 1)
            return Result.errorResult(902, "状态码不在范围内");

        Product product = productMapper.selectById(modifyProductStatusDto.getProductId());
        product.setStatus(modifyProductStatusDto.getStatus());
        productMapper.updateById(product);

        if(modifyProductStatusDto.getStatus() == 0)
            return Result.okResult("成功");
        else if (modifyProductStatusDto.getStatus() == 1)
            return Result.okResult("上架成功");
        return Result.errorResult(901, "无此ID对应商品");
    }

    @Override
    public Result addProductStock(AddProductStockDto addProductStockDto)
    {
        List<Integer> skuIds = addProductStockDto.getSkuIds();
        List<Integer> stocks = addProductStockDto.getStocks();

        if(skuIds.isEmpty() || stocks.isEmpty())
            return Result.errorResult(904, "sku和库存不能为空");
        if(skuIds.size() != stocks.size())
            return Result.errorResult(905, "sku和库存数组不等长");

        List<Sku> skus = skuService.listByIds(skuIds);
        for (int i = 0; i < skus.size(); i++)
            skus.get(i).setSkuStock(stocks.get(i));
        skuService.updateBatchById(skus);

        return Result.okResult("补货成功");
    }


}
