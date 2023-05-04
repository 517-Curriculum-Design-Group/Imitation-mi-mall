package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.enity.*;
import com.xiaomi_mall.enums.AppHttpCodeEnum;
import com.xiaomi_mall.exception.SystemException;
import com.xiaomi_mall.mapper.*;
import com.xiaomi_mall.service.CategoryService;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.vo.UserListPageVo;
import com.xiaomi_mall.vo.UserListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;
    private CategoryMapper categoryMapper;

    /**
     * 普通用户注册
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public Result register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return Result.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, nickName);
        if (count(queryWrapper) == SystemConstants.NICKNAME_NOT_EXIT) {
            return false;
        }
        return true;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        if (count(queryWrapper) == SystemConstants.USERNAME_NOT_EXIT) {
            return false;
        }
        return true;
    }

    /**
     * 管理员添加
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public Result addadmin(User user) {
        //防止注册的用户重名
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, user.getUserName());
        User user2 = getOne(queryWrapper);

        if (Objects.isNull(user2)) {
            //user2为空时，说明不重名，可以注册
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodePassword);
            user.setUserType("普通管理员");
            save(user);
            return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
    }

    @Override
    public Result getUserList(Integer pageNum, Integer pageSize, String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(nickName),User::getNickName, nickName);
        Page<User> pageInfo = new Page<>(pageNum, pageSize);
        page(pageInfo, queryWrapper);
        List<User> userList = pageInfo.getRecords();
        List<User> filterList = userList.stream()
                .filter(item -> Objects.equals(item.getUserType(), "普通用户"))
                .collect(Collectors.toList());
        int total = filterList.size();
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(filterList, UserListVo.class);
        UserListPageVo userListPageVo = new UserListPageVo(userListVos, total);
        return Result.okResult(userListPageVo);
    }

    @Override
    public Result updateUserStatus(Long userId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, userId);
        User user = getOne(queryWrapper);
        if (user.getStatus() == SystemConstants.USER_STATUS_NORMAL) {
            user.setStatus(1);
        }
        else if (user.getStatus() == SystemConstants.USER_STATUS_ERROR){
            user.setStatus(0);
        }
        updateById(user);
        return Result.okResult();
    }

    @Override
    public Result updateUserInfo(User user) {
        updateById(user);
        return Result.okResult();
    }

    @Override
    public Result getAdminList(Integer pageNum, Integer pageSize, String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(nickName),User::getNickName, nickName);
        Page<User> pageInfo = new Page<>(pageNum, pageSize);
        page(pageInfo, queryWrapper);
        List<User> userList = pageInfo.getRecords();
        List<User> filterList = userList.stream()
                .filter(item -> Objects.equals(item.getUserType(), "普通管理员"))
                .collect(Collectors.toList());
        int total = filterList.size();
        List<UserListVo> userListVos = BeanCopyUtils.copyBeanList(filterList, UserListVo.class);
        UserListPageVo userListPageVo = new UserListPageVo(userListVos, total);
        return Result.okResult(userListPageVo);
    }

    @Override
    public Result getHome()
    {
        Map<String, Object> res = new LinkedHashMap<>();

        //用户总量
        QueryWrapper<User> userSumWrapper = Wrappers.query();
        userSumWrapper.eq("user_type", "普通用户");
        List<User> userList = userMapper.selectList(userSumWrapper);
        res.put("userSum", userList.size());

        //用户周新增
        QueryWrapper<User> newUserCountWrapper = Wrappers.query();
        Date startDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7);
        Date endDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = dateFormat.format(startDate);
        String endTime = dateFormat.format(endDate);

        newUserCountWrapper
                .ge("create_time", startTime)
                .le("create_time", endTime)
                .eq("user_type", "普通用户");
        int newUserCount = userMapper.selectCount(newUserCountWrapper);
        res.put("newUserCount", newUserCount);

        //订单总量
        QueryWrapper<Order> orderSumWrapper = Wrappers.query();
        orderSumWrapper.eq("status", "3");
        int orderSum = orderMapper.selectCount(orderSumWrapper);
        res.put("orderSum", orderSum);

        //订单周新增
        QueryWrapper<Order> newOrderCountWrapper = Wrappers.query();
        newOrderCountWrapper
                .ge("order_time", startTime)
                .le("order_time", endTime)
                .eq("status", "3");
        int newOrderCount = orderMapper.selectCount(newOrderCountWrapper);
        res.put("newOrderCount", newOrderCount);

        //过去一个月中，每天的订单情况
        List<Map<String, Object>> statsList = orderMapper.getOrderCountEachDay();
        res.put("orderStatsList", statsList);

        //各类订单状态
        QueryWrapper<Order> orderDetailsWrapper = new QueryWrapper<>();
        orderDetailsWrapper
                .select("status", "count(1) as count")
                .groupBy("status");

        res.put("noPayOrder", 0);
        res.put("notSendOrder", 0);
        res.put("alreadySendOrder", 0);
        res.put("cancelOrder", 0);

        List<Map<String, Object>> maps = orderMapper.selectMaps(orderDetailsWrapper);
        for (Map<String, Object> map : maps)
        {
            String status = map.get("status").toString();
            Integer count = Integer.parseInt(map.get("count").toString());
            switch (status) {
                case "0":
                    res.put("noPayOrder", count);
                    break;
                case "1":
                    res.put("notSendOrder", count);
                    break;
                case "2":
                    res.put("alreadySendOrder", count);
                    break;
                case "4":
                    res.put("cancelOrder", count);
                    break;
                default:
                    break;
            }
        }

        //各类商品状态
        //正在销售的
        QueryWrapper<Product> onSaleProductCountWrapper = new QueryWrapper<>();
        onSaleProductCountWrapper.eq("status", "1");
        int onSaleProductCount = productMapper.selectCount(onSaleProductCountWrapper);
        res.put("onSaleProductCount", onSaleProductCount);

        //已下架的
        QueryWrapper<Product> notSaleProductCountWrapper = new QueryWrapper<>();
        notSaleProductCountWrapper.eq("status", "0");
        int notSaleProductCount = productMapper.selectCount(notSaleProductCountWrapper);
        res.put("notSaleProductCount", notSaleProductCount);

        //需要补货的：商品数量小于等于10则需补货
        QueryWrapper<Sku> skuWrapper = new QueryWrapper<>();
        skuWrapper
                .select( "product_id", "SUM(sku_stock) as stock")
                .eq("del_flag", "0")
                .groupBy("product_id")
                .having("SUM(sku_stock) <= {0}", 10);

        List<Sku> productStockLowList = skuMapper.selectList(skuWrapper);
        res.put("productStockLowCount", productStockLowList.size());

        return Result.okResult(res);
    }

    @Override
    public Result getUserHome() {
        Map<String, Object> res = new LinkedHashMap<>();

        //小米手机
        //红米手机
        //小米平板
        //红米笔记本
        //智能手环
        int cates[] = {59, 60, 67, 70, 77};
        String cateNames[] = {"小米手机", "红米手机", "小米平板", "红米笔记本", "智能手环"};
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<Product>();
        productQueryWrapper
                .eq("del_flag", 0)
                .eq("status", 1)
                .and(wrapper->wrapper.eq("category_id", 59)
                        .or().eq("category_id", 60)
                        .or().eq("category_id", 67)
                        .or().eq("category_id", 70)
                        .or().eq("category_id", 77)
                );
        List<Product> products = productMapper.selectList(productQueryWrapper);
        //System.out.println(products.size());

        Map<String, Object> headerProducts = new LinkedHashMap<>();
        for (int i = 0; i < cates.length; i++) {
            List<Map<String, Object>> sameCategoryProducts = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < products.size(); j++) {
                if(products.get(j).getCategoryId() != cates[i]) continue;
                Map<String, Object> singleProduct = new LinkedHashMap<>();
                singleProduct.put("product_id", products.get(j).getProductId());
                singleProduct.put("product_name", products.get(j).getProductName());
                singleProduct.put("product_pic", products.get(j).getProductPic());
                singleProduct.put("product_price", products.get(j).getLeastPrice());
                //singleProduct.put("product_price", products.get(j).getProductPic());
                sameCategoryProducts.add(singleProduct);
            }
            headerProducts.put(cateNames[i], sameCategoryProducts);
        }
        res.put("headerProducts", headerProducts);


        Map<String, Object> leftCategories = new LinkedHashMap<>();
        Map<String, Object> lowerProducts = new LinkedHashMap<>();
        List<Category> categories = categoryService.list();

        for (int i = 0; i < categories.size(); i++) {
            long parentId = categories.get(i).getParentId();
            if (parentId != -1) continue;

            List<Map<String, Object>> sameParentCategories = new ArrayList<>();
            List<Map<String, Object>> eachCategoryProduct = new ArrayList<>();
            int cateId = categories.get(i).getCategoryId();
            List<Category> filterList = categories.stream()
                    .filter(category -> category.getParentId() == cateId)
                    .collect(Collectors.toList());
            //System.out.println(filterList.size());
            //每一子种类入列
            for (Category category : filterList) {
                Map<String, Object> singleCategory = new LinkedHashMap<>();
                singleCategory.put("category_id", category.getCategoryId());
                singleCategory.put("category_name", category.getCategoryName());
                //singleCategory.put("category_pic", category.getCategoryId());
                sameParentCategories.add(singleCategory);

                Map<String, Object> eachCategory = new LinkedHashMap<>();
                eachCategory.put("category_name", category.getCategoryName());
                List<Product> tempProducts = new ArrayList<>();

                //找子类下的所有商品，最多填满八个
                QueryWrapper<Product> productQueryWrapper1 = new QueryWrapper<>();
                productQueryWrapper1.eq("category_id", category.getCategoryId());
                List<Product> tempProductList = productMapper.selectList(productQueryWrapper1);
                for (Product product:tempProductList) {
                    if(tempProducts.size() >= 8) break;
                    tempProducts.add(product);
                }
                eachCategory.put("products", tempProducts);
                eachCategoryProduct.add(eachCategory);

            }
            leftCategories.put(categories.get(i).getCategoryName(), sameParentCategories);
            lowerProducts.put(categories.get(i).getCategoryName(), eachCategoryProduct);
        }
        res.put("leftCategories", leftCategories);
        res.put("lowerProducts", lowerProducts);

        return Result.okResult(res);
    }
}
