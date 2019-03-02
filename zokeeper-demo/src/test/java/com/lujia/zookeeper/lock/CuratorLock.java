package com.lujia.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author :lujia
 * @date :2018/8/3  16:40
 */
public class CuratorLock {

    public static void main(String[] args) throws Exception {


        CuratorFramework curatorFramework= CuratorFrameworkFactory.newClient(
                "192.168.128.128:2181,192.168.128.129:2181,192.168.128.130:2181",
                new ExponentialBackoffRetry(2000,1));

        curatorFramework.start();


        InterProcessMutex interProcessMutex=new InterProcessMutex(curatorFramework,"/locks");



        if (interProcessMutex.acquire(2000, TimeUnit.MILLISECONDS)){

            try {
                printf("get lock success");
            }finally {
                interProcessMutex.release();
            }

        }
        System.in.read();

    }


    public static void printf(String message) {
        System.out.printf("[current thread is %s]--%s\n", Thread.currentThread().getName(), message);
    }
}
