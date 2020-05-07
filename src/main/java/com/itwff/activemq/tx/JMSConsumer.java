package com.itwff.activemq.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    public static final String ACTIVEMQ_URL="tcp://192.168.223.138:61616";
    public static final String QUEUE_NAME="queue01";

    public static  void main(String[] args)throws Exception
    {
        System.out.println("****我是2号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //3创建会话
        //两个参数。一个事务一个是签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);
        //5创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        /*while (true){
            // reveive() 一直等待接收消息，在能够接收到消息之前将一直阻塞。 是同步阻塞方式 。和socket的accept方法类似的。
             // reveive(Long time) : 等待n毫秒之后还没有收到消息，就是结束阻塞。
            // 因为消息发送者是 TextMessage，所以消息接受者也要是TextMessage

            TextMessage textMessage =(TextMessage) messageConsumer.receive();
            if(null!=textMessage){
                System.out.println("消费者接收到消息："+textMessage.getText());
            }else{
                break;
            }
        }*/


        //通过监听的方式获取消息
        messageConsumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if(message!=null && message instanceof TextMessage ){
                    TextMessage textMessage= (TextMessage) message;
                    try {
                        System.out.println("消费者接收到消息："+textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.commit();
        session.close();
        connection.close();
    }
}
