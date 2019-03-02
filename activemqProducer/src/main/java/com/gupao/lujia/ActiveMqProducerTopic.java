package com.gupao.lujia;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消息订阅模式，
 * topic
 *
 * @author Created by Administrator on 2018/7/8.
 */
public class ActiveMqProducerTopic {
    public static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqProducerTopic.class);

    public static void main(String[] agrs) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Destination lujiaTopic = session.createTopic("lujiaTopic");

            MessageProducer producer = session.createProducer(lujiaTopic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage textMessage = session.createTextMessage("hello lujia topic2");

            producer.send(textMessage);

            session.commit();
            LOGGER.info("topic 消息发送成功");
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
