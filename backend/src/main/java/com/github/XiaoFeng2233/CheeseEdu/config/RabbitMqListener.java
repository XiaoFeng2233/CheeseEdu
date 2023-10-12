package com.github.XiaoFeng2233.CheeseEdu.config;

import com.github.XiaoFeng2233.CheeseEdu.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = {"dead_order_expire_queue"})
public class RabbitMqListener {
    @Autowired
    private OrderService orderService;
    @RabbitHandler
    public void handler(String orderNum){
        orderService.updateOrderExpire(orderNum);
    }
}
