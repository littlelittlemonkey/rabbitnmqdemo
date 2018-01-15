package com.lyc.rabbit.publish;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 订阅者
 */
public class ReceiveLogs1 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //产生临时队列
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
    /**
     *打印结果
     [*] Waiting for messages. To exit press CTRL+C
     [x] Received 'Hello World! 0'
     [x] Received 'Hello World! 1'
     [x] Received 'Hello World! 2'
     [x] Received 'Hello World! 3'
     [x] Received 'Hello World! 4'
     */
}
