package com.gra.backend.service.utils;


import com.gra.backend.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UtilsService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RedisUtil redisUtil;
    public Map<String,String> sendEmail(String email){
        Date now = new Date();
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)); // 随机一个 4位长度的验证码
        System.out.println(code);

        //存入redis中，key为email ,value 为code ,时间为1min
        redisUtil.set(email,code,60);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("zzac0420@163.com");  // 发送人
        message.setTo(email);
        message.setSentDate(now);
        message.setSubject("【ZZAC Of Game】登录邮箱验证");
        message.setText("您本次登录的验证码是：" + code + "，有效期1分钟");
        javaMailSender.send(message);
        Map<String,String> map = new HashMap<>();
        map.put("error_message" ,"success");
        return map;

    }


}
