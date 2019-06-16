package com.springboot_Consumer;

import com.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

/**
 * Author: zhaoyuanguang
 * Date: 2019/6/16 13:25
 * Content:
 */
@Component
public class ReceiveHandler {
    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_EMAIL})
    public void receive_msg_email(String msg, Message message, Channel channel) {
        System.out.println(message);
        System.out.println(message.getBody());
    }

    @RabbitListener(queues = {RabbitConfig.QUEUE_INFORM_SMS})
    public void receive_msg_sms(String msg, Message message, Channel channel) {
        System.out.println(message);
        System.out.println(message.getBody());
    }
}
