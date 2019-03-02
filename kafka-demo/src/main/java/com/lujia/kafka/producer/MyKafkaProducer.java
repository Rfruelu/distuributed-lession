package com.lujia.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Created by lujia on 2018/7/14.
 */
public class MyKafkaProducer extends Thread {


    private static final Logger LOGGER = LoggerFactory.getLogger(MyKafkaProducer.class);


    private final KafkaProducer<Integer, String> kafkaProducer;

    private final String topic;

    public MyKafkaProducer(String topic) {

        Properties properties = new Properties();
        //kafka服务器地址可以是一个也可以是多个集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.0.101:9092,192.168.0.101:9093,192.168.0.101:9094");
        /**
         *acks模式
         * -1 需要所有的broker确认
         * 0 不需要broker的确认
         * 1 只需要获得kafka的leader确认
         */
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        // client.id
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "MyKafkaProducer");
        //key 序列化类
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerSerializer");
        //value序列化类
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;

    }

    @Override
    public void run() {

        int num = 0;
        while (num < 50) {

            String message = "message_" + num;
            System.out.println("开始发送消息:" + message);
            //发送消息 topic目标topic
            kafkaProducer.send(new ProducerRecord<Integer, String>(topic, message));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num++;
        }
    }

    public static void main(String[] args) {
        new MyKafkaProducer("test").start();
    }
}
