package com.itwff.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.soap.Text;

public class JMSConsumer_Persist {
    public static final String ACTIVEMQ_URL="tcp://192.168.223.138:61616";
    public static final String TOPIC_NAME="topic01";

    public static  void main(String[] args)throws Exception
    {
        System.out.println("****我是1号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("1号");
        connection.start();
        //3创建会话
        //两个参数。一个事务一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber=session.createDurableSubscriber(topic,"remark....");
        //启动
        connection.start();
        Message message = topicSubscriber.receive();
        while(null!=message){
            TextMessage textMessage=(TextMessage) message;
            System.out.println("*******收到的持久化topic"+textMessage.getText());
            message=topicSubscriber.receive(1000L);
        }
        session.close();
        connection.close();
    }
}
