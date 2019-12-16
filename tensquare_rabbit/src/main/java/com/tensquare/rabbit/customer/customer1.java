package com.tensquare.rabbit.customer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")
public class customer1 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("itcast接收到消息：" + msg);
    }

}
