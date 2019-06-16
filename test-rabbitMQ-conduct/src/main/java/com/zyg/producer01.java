package com.zyg;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.deploy.ui.FancyButton;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author: zhaoyuanguang
 * Date: 2019/6/15 23:13
 * Content:
 */
public class producer01 {
    //队列名称
    private static final String QUEUE = "helloworld";

    public static void main(String[] args) {
        Connection connection = null;
        Channel channel = null;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/"); //虚拟机名称，默认为“/”，相当于独立的服务

        try {
            //创建连接
            connection = factory.newConnection();
            //创建于exchange的通道，每个连接可以多个通道，每个通道代表一个会话任务
            channel = connection.createChannel();

            /**
             * 声明队列，如果Rabbit中没有此队列将自动创建
             * * param1:队列名称
             * * param2:是否持久化
             * * param3:队列是否独占此连接
             * * param4:队列不再使用时是否自动删除此队列
             * * param5:队列参数
             */
            channel.queueDeclare(QUEUE, true, false, false, null);

            //String message = "hello,rabbitMQ"+System.currentTimeMillis();
            /**
             * 消息发布方法
             * param1：Exchange的名称，如果没有指定，则使用Default Exchange
             * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * param3:消息包含的属性
             * param4：消息体
             */
            for (int i = 0; i < 10; i++) {
                String message = "hello,rabbitMQ"+System.currentTimeMillis()+"  "+i;
                channel.basicPublish("", QUEUE, null, message.getBytes());
                System.out.println("Send Message is:" + message + " ");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
