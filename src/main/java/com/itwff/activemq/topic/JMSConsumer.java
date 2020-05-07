package com.itwff.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    public static final String ACTIVEMQ_URL="tcp://192.168.223.138:61616";
    public static final String TOPIC_NAME="topic01";

    public static  void main(String[] args)throws Exception
    {
        System.out.println("****我是3号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3创建会话
        //两个参数。一个事务一个是签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Topic topic = session.createTopic(TOPIC_NAME);
        //5创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        //通过监听的方式获取TextMessage消息
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if(message!=null && message instanceof TextMessage ){
                    TextMessage textMessage= (TextMessage) message;
                    try {
                        System.out.println("消费者接收到topic消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //通过监听的方式获取mapMessage消息
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if(message!=null && message instanceof MapMessage ){
                    MapMessage mapMessage=(MapMessage) message;
                    try {
                        System.out.println("消费者接收到topic消息："+mapMessage.getString("k1"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
