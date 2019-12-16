package com.tensquare.rabbit.rabbitTest;

import com.tensquare.rabbit.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitApplication.class)
public class Product {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg1(){
        rabbitTemplate.convertAndSend("itcast", "分裂模式消息1");
    }

    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("itheima", "分裂模式消息2");
    }

    @Test
    public void sendMsg3(){
        rabbitTemplate.convertAndSend("xiaoyu", "分裂模式消息3");
    }

    @Test
    public void sendMsg4(){
        rabbitTemplate.convertAndSend("topic","good.abc.log", "主题模式");
    }
}
