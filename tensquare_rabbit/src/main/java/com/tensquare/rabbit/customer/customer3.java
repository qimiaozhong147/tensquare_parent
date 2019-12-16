package com.tensquare.rabbit.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "xiaoyu")
public class customer3 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("xiaoyu接收到消息：" + msg);
    }

}
