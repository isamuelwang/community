package com.owwang.community.mq.listener;

import com.aliyuncs.exceptions.ClientException;
import com.owwang.community.mq.util.AliMsgUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Classname SmsListener
 * @Description TODO
 * @Date 2020-01-12
 * @Created by WANG
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @RabbitHandler
    public void executeSms(Map<String,String> map) throws ClientException {
        String mobile = map.get("mobile");
        String checkcode = map.get("checkcode");
        System.out.println("手机号："+map.get("mobile"));
        System.out.println("验证码："+map.get("checkcode"));
        AliMsgUtil.sendSms(checkcode,mobile);
    }
}
