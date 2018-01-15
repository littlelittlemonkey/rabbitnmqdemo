package com.lyc.rabbit.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者程序，他负责发送日志消息，与之前不同的是它不是将消息发送到匿名交换器中，
 * 而是发送到一个名为【logs】的交换器中。我们提供一个空字符串的routingkey，
 * 它的功能被交换器的分发类型代替了。
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

//      分发消息
        for(int i = 0 ; i < 5; i++){
            String message = "Hello World! " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
