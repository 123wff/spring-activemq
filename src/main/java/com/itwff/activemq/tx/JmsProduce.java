package com.itwff.activemq.tx;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    public static final String ACTIVEMQ_URL="tcp://192.168.223.138:61616";
    public static final String QUEUE_NAME="queue01";

    public static void main(String[] args)throws JMSException
    {
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3创建会话
        //两个参数。一个事务一个是签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //创建消息的消费者
        MessageProducer messageProducer = session.createProducer(queue);
        //消息的持久化
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        //6通过使用messageProducer生产三条消息发送到MQ的队列里
        for(int i=1;i<=6;i++)
        {
            //创建消息
            TextMessage textMessage = session.createTextMessage("tx msg---" + i);
            messageProducer.send(textMessage);
        }
        //9关闭资源
        messageProducer.close();
        session.commit();
        session.close();
        connection.close();
        System.out.println("***********消息发送成功");

    }
}
