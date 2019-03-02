package com.lujia.zookeeper.connection;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author :lujia
 * @date :2018/7/29  16:35
 */
public class ConnectionDemo {

    public static void main(String[] args) throws InterruptedException {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper(
                    "192.168.128.128:2181,192.168.128.129:2181,192.168.128.130:2181",
                    4000,
                    (e) -> {
                        if (Watcher.Event.KeeperState.SyncConnected == e.getState()) {
                            printf();
                            countDownLatch.countDown();
                        }
                    });
            countDownLatch.await();
           /* try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            printf();


            //添加节点(can not create exists node throw exception=KeeperException$NodeExistsException)
           // zooKeeper.create("/zk-lujia","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            //Thread.sleep(1000);
            Stat stat=new Stat();

            //得到当前节点的value
            byte[] data = zooKeeper.getData("/zk-lujia", null, stat);
            System.out.println("value:"+new String(data));

            //update node value
           // zooKeeper.setData("/zk-lujia","my name".getBytes(),stat.getVersion());
           // byte[] dataWhenUpdate = zooKeeper.getData("/zk-lujia", null, stat);
           // System.out.println("update date then value:"+new String(dataWhenUpdate));

            /**
             * if the version is error
             * org.apache.zookeeper.KeeperException$BadVersionException: KeeperErrorCode = BadVersion for /zk-lujia
             */

            //zooKeeper.setData("/zk-lujia","my name is lujia ".getBytes(),0);

            //delete
            zooKeeper.delete("/zk-lujia",stat.getVersion());

            System.out.println(zooKeeper.getState());
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void printf(){
        System.out.printf("[currentThread is ] %s\n",Thread.currentThread().getName());
    }
}
