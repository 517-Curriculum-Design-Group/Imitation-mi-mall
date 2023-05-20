package com.xiaomi_mall.mq;

import com.xiaomi_mall.config.MyRabbitMQConfig;
import com.xiaomi_mall.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MQStockService {
    @Autowired
    private SeckillService seckillService;
    /**
     * 监听库存消息队列，并消费
     * @param productId
     */
    @RabbitListener(queues = MyRabbitMQConfig.STORY_QUEUE)
    public void decrByStock(Integer productId) {
        /**
         * 调用数据库service给数据库对应商品库存减一
         */
        log.info("减库存");
        seckillService.decrByStock(productId);
    }
}
