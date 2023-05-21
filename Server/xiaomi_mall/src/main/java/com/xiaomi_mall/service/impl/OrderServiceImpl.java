package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.dto.OrderCommit;
import com.xiaomi_mall.dto.SeckillOrderDto;
import com.xiaomi_mall.enity.*;
import com.xiaomi_mall.mapper.*;
import com.xiaomi_mall.service.OrderDetailService;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.SkuService;
import com.xiaomi_mall.service.UserService;
import com.xiaomi_mall.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.xiaomi_mall.enums.AppHttpCodeEnum.SKU_STOCK_LIMIT;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private OrderService orderService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SkuService skuService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public Result getBackOrderList(Integer pageNum, Integer pageSize, Integer status) {
        List<Order> orderList = orderMapper.getOrderList();
        List<Order> filterList;
        if(status != -1)
        {
            filterList = orderList.stream()
                    .filter(order -> order.getStatus() == status)
                    .collect(Collectors.toList());
        }
        else
        {
            filterList = orderList;
        }

        List<User> userList = userService.list();
        List<HashMap<String, Object>> res = new ArrayList<>();

        for (Order order : filterList) {
            HashMap<String, Object> map = new LinkedHashMap<>();
            map.put("orderId", order.getOrderId());
            map.put("orderTime", order.getOrderTime());
            map.put("totalPrice", order.getTotalPrice());
            map.put("userId", order.getUserId());
            String username = "";
            for (User user : userList) {
                if (user.getUserId() == order.getUserId()) {
                    username += user.getUserName();
                    break;
                }
            }
            map.put("userName", username);
            map.put("status", order.getStatus());
            res.add(map);
        }
        return Result.okResult(res);
    }

    @Override
    public Result getOrderDetail(Integer orderId) {
        Order order = orderMapper.getOrderByOrderId(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.getDetailListByOrderId(orderId);

        HashMap<String, HashMap<String, Object>> res = new LinkedHashMap<>();

        //订单相关
        HashMap<String, Object> map1 = new LinkedHashMap<>();
        map1.put("orderId", order.getOrderId());
        map1.put("orderTime", order.getOrderTime());
        res.put("orderDetail", map1);

        //商品相关
        HashMap<String, Object> map2 = new LinkedHashMap<>();
        List<HashMap<String, Object>> productList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            HashMap<String, Object> map = new LinkedHashMap<>();
            map.put("productName", orderDetail.getProductName());
            map.put("skuName", orderDetail.getSkuName());
            map.put("skuImage", orderDetail.getSkuImage());
            map.put("skuPrice", orderDetail.getSkuPrice());
            map.put("skuQuantity", orderDetail.getSkuQuantity());
            productList.add(map);
        }
        map2.put("productList", productList);
        map2.put("totalPrice", order.getTotalPrice());
        res.put("productDetail", map2);

        //收货相关
        HashMap<String, Object> map3 = new LinkedHashMap<>();
        map3.put("address", order.getAddress());
        map3.put("name", order.getName());
        map3.put("phone", order.getPhone());
        res.put("addressDetail", map3);
        return Result.okResult(res);
    }

    @Override
    public Result getUserOrderDetail(HttpServletRequest request, Integer orderId) {
        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
            if (userId == -1) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Order order = orderMapper.getOrderByOrderId(orderId);
        if(order.getUserId() != userId)
            return Result.errorResult(900, "非此用户的订单ID");

        List<OrderDetail> orderDetailList = orderDetailMapper.getDetailListByOrderId(orderId);

        HashMap<String, HashMap<String, Object>> res = new LinkedHashMap<>();

        //订单相关
        HashMap<String, Object> map1 = new LinkedHashMap<>();
        map1.put("orderId", order.getOrderId());
        map1.put("orderTime", order.getOrderTime());
        map1.put("orderStatus", order.getStatus());
        res.put("orderDetail", map1);

        //商品相关
        HashMap<String, Object> map2 = new LinkedHashMap<>();
        List<HashMap<String, Object>> productList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            HashMap<String, Object> map = new LinkedHashMap<>();
            map.put("productName", orderDetail.getProductName());
            map.put("skuName", orderDetail.getSkuName());
            map.put("skuImage", orderDetail.getSkuImage());
            map.put("skuPrice", orderDetail.getSkuPrice());
            map.put("skuQuantity", orderDetail.getSkuQuantity());
            productList.add(map);
        }
        map2.put("productList", productList);
        map2.put("totalPrice", order.getTotalPrice());
        res.put("productDetail", map2);

        //收货相关
        HashMap<String, Object> map3 = new LinkedHashMap<>();
        map3.put("address", order.getAddress());
        map3.put("name", order.getName());
        map3.put("phone", order.getPhone());
        res.put("addressDetail", map3);
        return Result.okResult(res);
    }


    @Override
    public Result checkOrder(HttpServletRequest request) {
        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
            if (userId == -1) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        QueryWrapper<Address> addressListWrapper = new QueryWrapper<>();
        addressListWrapper
                .eq("user_id", userId)
                .eq("del_flag", 0);
        List<Map<String, Object>> addresses = addressMapper.selectMaps(addressListWrapper);

        for (Map<String, Object> address : addresses) {
            address.remove("user_id");
            address.remove("is_default");
            address.remove("del_flag");
        }
        return Result.okResult(addresses);
    }

    @Override
    public Result generateOrder(HttpServletRequest request, List<OrderCommit> commits, Integer addressId) {
        //取userId
        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
            if (userId == -1) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //求单价*数量+总价 + //库存减少
        List<Integer> skuIds = new ArrayList<>();
        for (OrderCommit commit : commits) {
            skuIds.add(commit.getSkuId());
        }

        List<Sku> skus = skuMapper.selectBatchIds(skuIds);
        List<Double> eachPrices = new ArrayList<>();
        double sum = 0;
        for (int i = 0; i < commits.size(); i++) {
            double eachPrice = skus.get(i).getSkuPrice().doubleValue() * commits.get(i).getCommitCount();
            eachPrices.add(eachPrice);
            sum += eachPrice;
            //库存减少
            int restStock = skus.get(i).getSkuStock() - commits.get(i).getCommitCount();
            if (restStock < 0)
                return Result.errorResult(SKU_STOCK_LIMIT);
            skus.get(i).setSkuStock(restStock);
        }
        BigDecimal totalPrice = BigDecimal.valueOf(sum);

        //提交减少后的库存
        skuService.updateBatchById(skus);

        //得地址
        Address address = addressMapper.selectById(addressId);
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderTime(new Date());
        order.setTotalPrice(totalPrice);
        order.setStatus(1);//已支付未发货
        order.setAddress(address.getProvince() + address.getCity() + address.getDistrict() +
                address.getZhen() + address.getDetail());
        order.setName(address.getRecipientName());
        order.setPhone(address.getRecipientPhone());
        orderService.save(order);

        //添订单详情
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (int i = 0; i < commits.size(); i++) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setProductName(commits.get(i).getProductName());
            orderDetail.setSkuId(commits.get(i).getSkuId());
            orderDetail.setSkuName(skus.get(i).getSkuName());
            orderDetail.setSkuImage(skus.get(i).getSkuImage());
            orderDetail.setSkuPrice(skus.get(i).getSkuPrice());
            orderDetail.setSkuQuantity(commits.get(i).getCommitCount());
            orderDetailList.add(orderDetail);
        }
        orderDetailService.saveBatch(orderDetailList);
        return Result.okResult("订单支付成功！");
    }

    @Override
    public Result getOrderList(HttpServletRequest request) {
        //取userId
        long userId = -1;
        try {
            userId = JwtUtil.getUserId(request);
            if (userId == -1) throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("user_id", userId);
        List<Map<String, Object>> orderList = orderMapper.selectMaps(orderQueryWrapper);

        List<HashMap<String, Object>> res = new ArrayList<>();
        for (Map<String, Object> order : orderList) {
            HashMap<String, Object> map = new LinkedHashMap<>();
            map.put("order_id", order.get("order_id"));
            map.put("order_time", order.get("order_time"));
            map.put("total_price", order.get("total_price"));
            map.put("status", order.get("status"));
            res.add(map);
        }
        return Result.okResult(res);
    }

    @Override
    public Result orderDelivery(List<Integer> orderId) {
        List<Order> orders = orderMapper.selectBatchIds(orderId);
        for (Order order : orders) {
            if (order.getStatus() == 1)
                order.setStatus(2);
        }
        orderService.updateBatchById(orders);
        return Result.okResult("选中的已支付订单已通知发货！");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createSeckillOrder(SeckillOrderDto seckillOrderDto) {
        int skuId = seckillOrderDto.getSkuId();
        String productName = seckillOrderDto.getProductName();

        //创建订单
        orderService.save(seckillOrderDto.getOrder());

        LambdaQueryWrapper<Sku> skuWrapper = new LambdaQueryWrapper<>();
        skuWrapper.eq(Sku::getSkuId, skuId);
        String skuImage = skuMapper.selectOne(skuWrapper).getSkuImage();

        //创建订单详情
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(seckillOrderDto.getOrder().getOrderId());
        orderDetail.setProductName(productName);
        orderDetail.setSkuId(skuId);
        orderDetail.setSkuImage(skuImage);
        orderDetail.setSkuPrice(seckillOrderDto.getOrder().getTotalPrice());
        orderDetail.setSkuQuantity(1);

        orderDetailService.save(orderDetail);
    }


}