package com.zyg;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Author: zhaoyuanguang
 * Date: 2019/6/15 23:28
 * Content:
 */
public class consumer04_topics_email {
    private static String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static String EXCHANGE_TOPICS_INFORM = "exchange_topics_inform";

    public static void main(String[] args)throws Exception {
        Connection connection = null;
        Channel channel = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");//rabbitmq默认虚拟机名称为“/”，虚拟机相当于一个独立的mq服务器
        //创建连接
        connection = factory.newConnection();
        //创建于exchange的通道，每个连接可以多个通道，每个通道代表一个会话任务
        channel = connection.createChannel();
        //channel.queueDeclare(QUEUE, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);

        channel.queueDeclare(QUEUE_INFORM_EMAIL, true, false, false, null);

        channel.queueBind(QUEUE_INFORM_EMAIL, EXCHANGE_TOPICS_INFORM, QUEUE_INFORM_EMAIL);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String exchange = envelope.getExchange();
                String routingKey = envelope.getRoutingKey();
                long deliveryTag = envelope.getDeliveryTag();
                String msg = new String(body, "utf-8");
                System.out.println(msg);
            }
        };
        channel.basicConsume(QUEUE_INFORM_EMAIL, true, defaultConsumer);



    }
}
