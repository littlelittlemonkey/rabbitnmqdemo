package com.lyc.rabbit.routing;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 *
 *
 */
public class RoutingSendDirect {

    private static final String EXCHANGE_NAME = "direct_logs";
    // 路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//      声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//      发送消息
        for(String severity :routingKeys){
            String message = "Send the message level:" + severity;
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        }
        channel.close();
        connection.close();
    }
    /**
     *输出结果
     [x] Sent 'info':'Send the message level:info'
     [x] Sent 'warning':'Send the message level:warning'
     [x] Sent 'error':'Send the message level:error'
     */
}
