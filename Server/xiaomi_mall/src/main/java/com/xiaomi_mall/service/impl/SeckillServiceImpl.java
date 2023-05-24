package com.xiaomi_mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaomi_mall.config.MyRabbitMQConfig;
import com.xiaomi_mall.config.Result;
import com.xiaomi_mall.constants.SystemConstants;
import com.xiaomi_mall.dto.SeckillOrderDto;
import com.xiaomi_mall.enity.*;
import com.xiaomi_mall.mapper.ProductMapper;
import com.xiaomi_mall.mapper.SeckillMapper;
import com.xiaomi_mall.mapper.SkuMapper;
import com.xiaomi_mall.mapper.UserMapper;
import com.xiaomi_mall.mq.MQOrderService;
import com.xiaomi_mall.service.AddressService;
import com.xiaomi_mall.service.OrderService;
import com.xiaomi_mall.service.ProductService;
import com.xiaomi_mall.service.SeckillService;
import com.xiaomi_mall.util.BeanCopyUtils;
import com.xiaomi_mall.util.RedisCache;
import com.xiaomi_mall.util.SecurityUtils;
import com.xiaomi_mall.vo.SeckillListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {
    @Autowired
    private SeckillMapper seckillMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AddressService addressService;
    @Autowired
    private MQOrderService mqOrderService;
    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Override
    public Result seckill(Integer productId) {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Seckill> seckillWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserId, userId);
        wrapper.eq(Product::getProductId, productId);
        seckillWrapper.eq(Seckill::getProductId, productId);
        User user = userMapper.selectOne(queryWrapper);
        Product product = productMapper.selectOne(wrapper);
        Seckill seckill = seckillMapper.selectOne(seckillWrapper);

        int skuId = seckill.getSkuId();
        BigDecimal seckillPrice = seckill.getSeckillPrice();

        Object cacheObject = redisCache.getCacheObject("SeckillUserId:" + userId);

        String message = null;
        if (cacheObject == null) {
            logger.info("参加秒杀的用户是: {}, 秒杀的商品时: {}", user.getUserName(), product.getProductName());


            Long decrByResult = redisCache.decrBy("productId+" + productId);
            //调用redis给相应商品库存量减一
            if (decrByResult >= 0) {
                /**
                 * 说明该商品的库存量有剩余，可以进行下订单操作
                 */
                logger.info("用户：{}秒杀该商品：{}库存有余，可以进行下订单操作", user.getUserName(), product.getProductName());
                //发消息给库存消息队列，将库存数据减一
                rabbitTemplate.convertAndSend(MyRabbitMQConfig.STORY_EXCHANGE, MyRabbitMQConfig.STORY_ROUTING_KEY, productId);
                addressService.getDefaultAddress(userId);

                //发消息给订单消息队列，创建订单
                Order order = new Order();
                order.setUserId(userId);
                order.setTotalPrice(seckillPrice);
                order.setOrderTime(new Date());
                order.setStatus(1);//已支付未发货
                order.setAddress(getDefaultAddress1(userId));
                order.setName(getDefaultAddress2(userId).getRecipientName());
                order.setPhone(getDefaultAddress2(userId).getRecipientPhone());

                SeckillOrderDto seckillOrderDto = new SeckillOrderDto();
                seckillOrderDto.setProductName(product.getProductName());
                seckillOrderDto.setOrder(order);
                seckillOrderDto.setSkuId(skuId);
//            MQOrderService mqOrderService = new MQOrderService();
//            mqOrderService.createSeckillOrder(seckillOrderDto);
                rabbitTemplate.convertAndSend(MyRabbitMQConfig.ORDER_EXCHANGE, MyRabbitMQConfig.ORDER_ROUTING_KEY, seckillOrderDto);
                //orderService.createSeckillOrder(seckillOrderDto);
                redisCache.setCacheObject("SeckillUserId:"+ userId, 1);
                redisCache.expire("SeckillUserId:"+ userId, 5000000);
                message = "恭喜，您秒杀商品" + product.getProductName() + "成功";
            } else {
                /**
                 * 说明该商品的库存量没有剩余，直接返回秒杀失败的消息给用户
                 */
                // logger.info("用户：{}秒杀时商品的库存量没有剩余,秒杀结束", userName);
                message = "商品" + product.getProductName() + "的库存量没有剩余,秒杀结束";
            }

            return Result.okResult(message);
        } else {
            message = "您已经参与秒杀过" + product.getProductName() + "商品了，不能继续参加秒杀";
            return Result.okResult(message);
        }

    }

    @Override
    public void decrByStock(Integer productId) {
        //校验库存
        LambdaQueryWrapper<Seckill> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seckill::getProductId, productId);
        int store = seckillMapper.selectOne(queryWrapper).getStockCount();
        if (store >= 1) {
            seckillMapper.decraystore(productId);
        }
    }

    @Override
    public Result addSeckill(Seckill seckill) {
        LambdaQueryWrapper<Seckill> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Seckill::getProductId, seckill.getProductId());
        Seckill seckill1 = seckillMapper.selectOne(queryWrapper);

        if (Objects.isNull(seckill1)) {
            save(seckill);
            redisCache.setCacheObject("productId+" + seckill.getProductId(), seckill.getStockCount());
            return Result.okResult("添加秒杀商品成功");
        } else {
            return Result.errorResult(601, "秒杀商品已经存在，不能添加");
        }
    }

    @Override
    public Result getSeckillProd() {
        List<Product> list = productService.list();
        List<Product> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            LambdaQueryWrapper<Sku> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Sku::getProductId, list.get(i).getProductId());
            List<Sku> skus = skuMapper.selectList(queryWrapper);

            LambdaQueryWrapper<Seckill> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Seckill::getProductId, list.get(i).getProductId());
            Seckill seckill = seckillMapper.selectOne(wrapper);
            if (skus.size() > 0 && Objects.isNull(seckill)) {
                res.add(list.get(i));
            }
        }

        return Result.okResult(res);
    }

    @Override
    public Result getSelectProd(Product product) {
        int productId = product.getProductId();
        return Result.okResult(productId);
    }

    @Override
    public Result getSeckillSku(int productId) {
        LambdaQueryWrapper<Sku> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sku::getProductId, productId);
        List<Sku> skus = skuMapper.selectList(queryWrapper);
        return Result.okResult(skus);
    }

    @Override
    public Result getSelectSku(Sku sku) {
        Integer skuId = sku.getSkuId();
        return Result.okResult(skuId);
    }

    @Override
    public Result getSeckillList() {
        List<Seckill> list = list();
        List<SeckillListVo> seckillListVos = BeanCopyUtils.copyBeanList(list, SeckillListVo.class);
        for (int i = 0; i < seckillListVos.size(); i++) {

            Integer productId = seckillListVos.get(i).getProductId();
            Integer skuId = seckillListVos.get(i).getSkuId();
            Product product = productMapper.selectById(productId);
            String skuName = skuMapper.selectById(skuId).getSkuName();
            seckillListVos.get(i).setProductName(product.getProductName());
            seckillListVos.get(i).setProductPic(product.getProductPic());
            seckillListVos.get(i).setSkuName(skuName);

        }
        return Result.okResult(seckillListVos);
    }

    @Override
    public Result getFollSecList1() {
        LambdaQueryWrapper<Seckill> queryWrapper = new LambdaQueryWrapper<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        queryWrapper.gt(Seckill::getStartTime, format);
        List<Seckill> list = seckillMapper.selectList(queryWrapper);
        List<SeckillListVo> seckillListVos = BeanCopyUtils.copyBeanList(list, SeckillListVo.class);
        for (int i = 0; i < seckillListVos.size(); i++) {

            Integer productId = seckillListVos.get(i).getProductId();
            Integer skuId = seckillListVos.get(i).getSkuId();
            Product product = productMapper.selectById(productId);
            String skuName = skuMapper.selectById(skuId).getSkuName();
            seckillListVos.get(i).setProductName(product.getProductName());
            seckillListVos.get(i).setProductPic(product.getProductPic());
            seckillListVos.get(i).setSkuName(skuName);

        }

        return Result.okResult(seckillListVos);
    }

    @Override
    public Result getFollSecList2() {
        LambdaQueryWrapper<Seckill> queryWrapper = new LambdaQueryWrapper<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        queryWrapper.le(Seckill::getStartTime, format)
                .ge(Seckill::getEndTime, format);
        List<Seckill> list = seckillMapper.selectList(queryWrapper);
        List<SeckillListVo> seckillListVos = BeanCopyUtils.copyBeanList(list, SeckillListVo.class);
        for (int i = 0; i < seckillListVos.size(); i++) {

            Integer productId = seckillListVos.get(i).getProductId();
            Integer skuId = seckillListVos.get(i).getSkuId();
            Product product = productMapper.selectById(productId);
            String skuName = skuMapper.selectById(skuId).getSkuName();
            seckillListVos.get(i).setProductName(product.getProductName());
            seckillListVos.get(i).setProductPic(product.getProductPic());
            seckillListVos.get(i).setSkuName(skuName);

        }

        return Result.okResult(seckillListVos);
    }

    @Override
    public Result getFollSecList3() {
        LambdaQueryWrapper<Seckill> queryWrapper = new LambdaQueryWrapper<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        queryWrapper.lt(Seckill::getEndTime, format);
        List<Seckill> list = seckillMapper.selectList(queryWrapper);
        List<SeckillListVo> seckillListVos = BeanCopyUtils.copyBeanList(list, SeckillListVo.class);
        for (int i = 0; i < seckillListVos.size(); i++) {

            Integer productId = seckillListVos.get(i).getProductId();
            Integer skuId = seckillListVos.get(i).getSkuId();
            Product product = productMapper.selectById(productId);
            String skuName = skuMapper.selectById(skuId).getSkuName();
            seckillListVos.get(i).setProductName(product.getProductName());
            seckillListVos.get(i).setProductPic(product.getProductPic());
            seckillListVos.get(i).setSkuName(skuName);

        }

        return Result.okResult(seckillListVos);
    }

    private String getDefaultAddress1(Long userId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId)
                .eq(Address::getIsDefault, SystemConstants.DEFAULT_ADDRESS);
        Address address = addressService.getOne(wrapper);
        return address.getProvince() + address.getCity() + address.getDistrict() +
                address.getZhen() + address.getDetail();
    }

    private Address getDefaultAddress2(Long userId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId)
                .eq(Address::getIsDefault, SystemConstants.DEFAULT_ADDRESS);
        Address address = addressService.getOne(wrapper);
        return address;
    }
}
