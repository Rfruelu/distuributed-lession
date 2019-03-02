package com.lujia.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @author Created by lujia on 2018/7/15.
 */
public class MyKafkaConsumerTwo extends  Thread{


    private static final Logger LOGGER= LoggerFactory.getLogger(MyKafkaConsumerTwo.class);

    private final KafkaConsumer kafkaConsumer;


    public MyKafkaConsumerTwo(String topic){

        Properties properties=new Properties();
        //kafka服务器地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "192.168.0.101:9092,192.168.0.101:9093,192.168.0.101:9094");
        /**
         * group.id 消费组,
         * 同一个组内的producer是竞争 关系 ，相当于activeMQ的queue
         * 不通组的producer都可以去消费消息，相当于一个发布--订阅模式，)
         */
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"MyKafkaConsumer1");
        //是否开启自动提交确认消息（true||false）
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        //自动提交的间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //key的反序列化类
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        //value的反序列化类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        /**
         * auto.offset.reset :有一下几种设置
         *  earliest 新加入的消费组都可以重新去消费
         *  latest 新加入的消费组从已消费的最大offset开始消费
         * none 新加入的消费组如果没有offset，抛出异常
         */
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        kafkaConsumer=new KafkaConsumer(properties);
        //要消费的 topic
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public void run() {

        while (true){
            //一次poll 1000消息
            ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(1000);

            for (ConsumerRecord<Integer, String> consumerRecord : consumerRecords) {
                //消息value
                String value = consumerRecord.value();
                //消息所在的分区
                int partition = consumerRecord.partition();
                /**
                 *  如果ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG设置false，需要手动去提交确认消息
                 *  kafkaConsumer.commitAsync() --  异步确认消息
                 *  kafkaConsumer.commitSync() --同步确认
                 */
                //kafkaConsumer.commitSync();

                LOGGER.error("revier message:{}---分区:{}",value,partition);

            }

        }
    }

    public static void main(String[] args) {
        new MyKafkaConsumerTwo("test").start();
    }
}
