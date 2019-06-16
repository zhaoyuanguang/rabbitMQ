package com.zyg;

import com.rabbitmq.client.*;

import javax.swing.event.ChangeEvent;
import java.io.IOException;

/**
 * Author: zhaoyuanguang
 * Date: 2019/6/15 23:28
 * Content:
 */
public class consumer01 {
    private static String QUEUE = "helloworld";

    public static void main(String[] args)throws Exception {
        Connection connection = null;
        Channel channel = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        //创建连接
        connection = factory.newConnection();
        //创建于exchange的通道，每个连接可以多个通道，每个通道代表一个会话任务
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
               //交换机
                String exchange = envelope.getExchange();
                //路由key
                String routingKey = envelope.getRoutingKey();
                //消息id
                long deliveryTag = envelope.getDeliveryTag();
                //消息内容
                String msg = new String(body, "utf-8");
                System.out.println("receive massage:" + msg);
            }
        };
        channel.basicConsume(QUEUE, true, consumer);


    }
}
