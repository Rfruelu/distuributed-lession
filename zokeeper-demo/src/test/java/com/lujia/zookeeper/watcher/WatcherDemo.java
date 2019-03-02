package com.lujia.zookeeper.watcher;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author :lujia
 * @date :2018/8/1  16:32
 */
public class WatcherDemo {

    public static void main(String[] args) {
        CountDownLatch countDownLatch=new CountDownLatch(1);
        try {
            final ZooKeeper
            zooKeeper=new ZooKeeper(
                    "192.168.128.128:2181,192.168.128.129:2181,192.168.128.130:2181"
            ,4000,
                    (e)->{
                        printf("default event "+e.getType());

                        if (Watcher.Event.KeeperState.SyncConnected==e.getState()){
                            // the connection is connected
                            countDownLatch.countDown();
                        }
                    });

            countDownLatch.await();


            //create a persistent node
           zooKeeper.create("/zk-persistent-lujia","my name".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            //exists getData getChildren
            //bind a watcher with exists
            String path="/zk-persistent-lujia";
            Stat stat = zooKeeper.exists(path,
                    (event) -> {
                        printf(event.getType() + "--->" + event.getPath());

                        //再一次去绑定事件
                        try {
                            zooKeeper.exists(event.getPath(),true);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    });

            stat=zooKeeper.setData(path,"123".getBytes(),stat.getVersion());
            Thread.sleep(1000);

            zooKeeper.delete(path,stat.getVersion());

            zooKeeper.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }


    }


    public static void printf(String message){
        System.out.printf("[currentThread is %s] %s\n",Thread.currentThread().getName(),message);
    }

}
