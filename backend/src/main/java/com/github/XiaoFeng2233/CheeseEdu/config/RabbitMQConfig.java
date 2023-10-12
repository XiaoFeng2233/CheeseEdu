package com.github.XiaoFeng2233.CheeseEdu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    @Value("${order.expire}")
    private int orderExpire;

    /**
     * 配置订单队列交换机
     * @return
     */
    @Bean
    public DirectExchange directOrderExchange(){
        return new DirectExchange("direct_order_exchange",true,false);
    }

    /**
     * 配置订单队列
     * @return
     */
    @Bean
    public Queue orderQueue(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("x-message-ttl",orderExpire);
        map.put("x-dead-letter-routing-key","order.dead.expire.notice");
        map.put("x-dead-letter-exchange","dead_order_exchange");
        return new Queue("order_expire_queue",true,false,false,map);
    }

    /**
     * 将交换机与队列进行绑定
     * @return
     */
    @Bean
    public Binding orderQueueBinding(){
        return BindingBuilder.bind(orderQueue()).to(directOrderExchange()).with("order.ttl.expire.notice");
    }

    /**
     *  创建死信队列
     * @return
     */
    @Bean
    public Queue deadOrderQueue(){
        return new Queue("dead_order_expire_queue",true);
    }

    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    public DirectExchange deadOrderExchange(){
        return new DirectExchange("dead_order_exchange",true,false);
    }

    /**
     * 绑定死信队列
     * @return
     */
    @Bean
    public Binding deadOrderQueueBinding(){
        return BindingBuilder.bind(deadOrderQueue()).to(deadOrderExchange()).with("order.dead.expire.notice");
    }





}
