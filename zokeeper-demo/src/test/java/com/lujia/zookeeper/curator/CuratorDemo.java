package com.lujia.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * @author :lujia
 * @date :2018/8/1  17:05
 */
public class CuratorDemo {

    public static void main(String[] args) {


        CuratorFramework curatorFramework=
                        CuratorFrameworkFactory.builder()
                        .connectString("192.168.128.128:2181,192.168.128.129:2181,192.168.128.130:2181")
                        .retryPolicy(new ExponentialBackoffRetry(1000,3))
                        .connectionTimeoutMs(3000)
                        .sessionTimeoutMs(2000)
                                .namespace("curator")
                        .build();
        try {
            curatorFramework.start();
            //create zNode with the given path and given value
            // curatorFramework.create().forPath("/zk-curztor", "lujia".getBytes());


            /**
             * getData
             */
            byte[] bytes = curatorFramework.getData().forPath("/zk-curztor");

            System.out.println(new String(bytes));


            //setData

            curatorFramework.setData().forPath("/zk-curztor","321".getBytes());

            Stat stat=new Stat();
            byte[] storingStatIn = curatorFramework.getData().storingStatIn(stat).forPath("/zk-curztor");

            System.out.println(new String(storingStatIn));

            //delete
            curatorFramework.delete().forPath("/zk-curztor");


            //create temp zNode 无序的节点
           //curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/zk-curator-temp","temp".getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
