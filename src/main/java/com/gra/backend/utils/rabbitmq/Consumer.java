package com.gra.backend.utils.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class Consumer {
    /**
     * @param message 参数message为消息的内容
     */

    public void consume(String message) {
        System.out.println("rabbitmq" + message);
    }

}
