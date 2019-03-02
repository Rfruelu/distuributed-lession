package com.lujia.active;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消息订阅模式：消息订阅者
 *
 * @author Created by Administrator on 2018/7/8.
 */
public class ActiveMqConsumerTopicThree {

    public static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqConsumerTopicThree.class);

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
           // connection.setClientID("two");
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            Destination lujiaTopic = session.createTopic("lujiaTopic");

            MessageListener messageListener=new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof  TextMessage){
                        try {
                            String text = ((TextMessage) message).getText();
                            LOGGER.info("topic 订阅者收到消息:{}",text);
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };

            MessageConsumer consumer = session.createConsumer(lujiaTopic);
            while (true){
                consumer.setMessageListener(messageListener);
                session.commit();

            }

            /*TextMessage receive = (TextMessage) consumer.receive();
            String text = receive.getText();
            LOGGER.info("topic 订阅者收到消息:{}",text);*/

            // session.commit();

            // session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
