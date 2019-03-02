package com.lujia.active;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * activemq 消息消费者  consumer
 *
 * @author Created by Administrator on 2018/7/8.
 */
public class ActiveMqConsumer {

    public static final Logger LOGGER = LoggerFactory.getLogger(ActiveMqConsumer.class);

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();

            connection.start();

            /**
             * consumer 非事务性
             * 设置AUTO_ACKNOWLEDGE 自动确认
             * CLIENT_ACKNOWLEDGE 需要consumer手动确认 ,message.acknowledge()手动执行确认
             * DUPS_OK_ACKNOWLEDGE 延迟确认
             */
            Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
            Destination lujiaQueue = session.createQueue("lujiaQueue");
            MessageConsumer consumer = session.createConsumer(lujiaQueue);


            for (int i=0;i<10;i++){

                TextMessage textMessage = (TextMessage) consumer.receive();
                String text = textMessage.getText();
                LOGGER.info("接收到消息:{}--{}", text,i);

               // if (i==6){

                    textMessage.acknowledge();
                //}
            }
            /*MessageListener messageListener =new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        String text = ((TextMessage) message).getText();
                        message.acknowledge();//在非事务性会话中，执行手动确认消息
                        LOGGER.info("接收到消息:{}",text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            };
            while (true){

                consumer.setMessageListener(messageListener);

            }*/
            // session.commit();
            //  session.close();

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
