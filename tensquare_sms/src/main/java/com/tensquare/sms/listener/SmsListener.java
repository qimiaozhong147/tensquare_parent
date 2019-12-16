package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.smsUtils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;

    @Value("${aliyun.sms.sing_name}")
    private String sing_name;

    @RabbitHandler
    public void sendSms(Map<String, String> message){
        System.out.println("手机号" + message.get("mobile"));
        System.out.println("验证码" + message.get("checkCode"));
        String checkCode = message.get("checkCode");
        try {
            smsUtil.sendSms(message.get("mobile"), template_code, sing_name, "{\"code\":\""+ checkCode +"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


}
