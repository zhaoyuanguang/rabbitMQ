package com.springboot_Producer;

import com.config.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: zhaoyuanguang
 * Date: 2019/6/16 12:01
 * Content:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Producer_topics_springboot {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //@Test
    @Test
    public void testSend() {
        for (int i = 0; i < 10; i++) {
            String message = "sms email inform to user" + i;
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_TOPICS_INFORM,
                                "inform.sms.email",
                                            message);
        }
    }
}
