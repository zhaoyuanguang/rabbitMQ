package com.springboot_Consumer;

import com.config.RabbitConfig;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.auth.login.Configuration;
import java.io.UnsupportedEncodingException;

/**
 * Author: zhaoyuanguang
 * Date: 2019/6/16 12:06
 * Content:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Consumer_topics_springboot {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testConsumer() throws UnsupportedEncodingException {
        Message receive = rabbitTemplate.receive(RabbitConfig.QUEUE_INFORM_EMAIL);
        byte[] body = receive.getBody();
        String s = new String(body, "utf-8");
        System.out.println(s);

    }
}
