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
 *
 * activemq 消息生产者 点对点
 * destination、messageProducer、message 都是session创建
 *
 * 在持久化和非持久化的模式下有不通的消息发送策略，（消息的发送有异步和同步两种方式）
 * 1 、设置持久化消息？ producer.setDeliveryMode(DeliveryMode.PERSISTENT);
 * 2、  在非持久化的模式下默认是异步发送消息，
 *      非持久化并且非事务的模式下是同步发送消息
 *      在开启事务的情况下，都是异步发送消息
 *  3、 手动设置异步发送消息
 *          1、 new ActiveMQConnectionFactory("tcp://localhost:61616?jms.useAsynSend=true");
 *          2、((ActiveMQConnectionFactory)connectionFactory).setUseAsyncSend(true);
 *
 *
 * @author Created by Administrator on 2018/7/7.
 */
public class ActiveMqProducer {

    public static final Logger LOGGER= LoggerFactory.getLogger(ActiveMqProducer.class);

    public static void main(String[] args){

        //创建一个connectionFactory tcp://localhost:61616  mq服务的地址
        /**
         * new ActiveMQConnectionFactory("tcp://localhost:61616?jms.useAsynSend=true"); 设置异步发送消息
         */
        ConnectionFactory connectionFactory =new ActiveMQConnectionFactory("tcp://localhost:61616");
        ((ActiveMQConnectionFactory)connectionFactory).setUseAsyncSend(true);
        Connection connection=null;
        try {
            //通过连接工程创建一个连接
            connection = connectionFactory.createConnection();
            //start
            connection.start();
            //通过connection创建一个会话
            /**
             * Boolean.TRUE :设置这个session是事务性的,消息的提交需要手动session.commit();
             * Boolean.FALSE:非事务性的，不存在事务，此时第二个参数var2
             *  AUTO_ACKNOWLEDGE = 1; 自动确认
             */
            Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            //destination 消息的目的地，
            Destination destination=session.createQueue("lujiaQueue");
            //消息生产者 producer
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);//持久化消息（这里的持久化是指：消息保存在broker的磁盘上，broker故障的情况下，可以恢复消息）
           // producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);非持久化消息
            for (int i = 0; i < 10; i++) {
                TextMessage textMessage =session.createTextMessage("Hello lujia activeMq ! "+i);

                //发送消息
                producer.send(textMessage);
                //事务性的，所以要提交
                //session.commit();
                LOGGER.info("消息发送成功");

            }

            //也可以回滚
            //session.rollback();
            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            if (null!=connection){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
