package com.itwff.activemq.topic;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    public static final String ACTIVEMQ_URL="tcp://192.168.223.138:61616";
    public static final String TOPIC_NAME="topic01";

    public static void main(String[] args)throws JMSException
    {
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3创建会话
        //两个参数。一个事务一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息的消费者
        MessageProducer messageProducer = session.createProducer(topic);
        //6通过使用messageProducer生产三条消息发送到MQ的队列里
        for(int i=1;i<=3;i++)
        {
            //创建消息
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME---" + i);
            //8 通过messageProducer发送到mq
            messageProducer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1"," mapMessage --- v2");
            messageProducer.send(mapMessage);
        }
        //9关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("***********TOPIC_NAME 消息发送成功");

    }
}
