package com.xiaomi_mall.mq;

import com.xiaomi_mall.config.MyRabbitMQConfig;
import com.xiaomi_mall.dto.SeckillOrderDto;
import com.xiaomi_mall.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class MQOrderService {
    @Autowired
    private OrderService orderService;
    /**
     * 监听订单消息队列，并消费
     *
     * @param id
     */
    @RabbitListener(queues = MyRabbitMQConfig.ORDER_QUEUE)
    public void createSeckillOrder(@RequestBody SeckillOrderDto seckillOrderDto) {
        log.info("收到订单消息");
        orderService.createSeckillOrder(seckillOrderDto);
    }
}
