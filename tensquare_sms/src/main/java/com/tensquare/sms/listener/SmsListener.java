package com.tensquare.sms.listener;

import com.tensquare.sms.util.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/19 0019
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @Value("${aliyun.sms.template_code}")
    private String template_code;//模板编号
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    @RabbitHandler
    public void sendSms(Map<String, String> message) {
        System.out.println("手机号：" + message.get("mobile"));
        System.out.println("验证码：" + message.get("code"));
        //暂时无法使用
//        try {
//            smsUtil.sendSms(message.get("mobile"), template_code, sign_name, "{\\\"number\\\":\\\"\"+ map.get(\"code\") +\"\\\"}\"");
//            } catch(Exception e){
//                e.printStackTrace();
//            }

        }
    }
