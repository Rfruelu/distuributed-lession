package com.lujia.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 使用zookeeper 实现分布锁
 * 利用zookeeper 节点的有序性
 *
 * @author :lujia
 * @date :2018/8/2  16:56
 */
public class DistributionLock implements Lock, Watcher {


    private ZooKeeper zooKeeper = null;

    //定义一个根节点
    private String ROOR_LOCK = "/locks";
    //当前锁
    private String CURRENT_LOCK;
    //上一个等待的锁
    private String WAIT_LOCK;

    private CountDownLatch countDownLatch;



    public DistributionLock() {

        try {
            zooKeeper = new ZooKeeper("192.168.128.128:2181,192.168.128.129:2181,192.168.128.130:2181",
                    3000, this);

            Stat stat = zooKeeper.exists(ROOR_LOCK, false);

            if (stat == null) {
                zooKeeper.create(ROOR_LOCK, "root".getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        if (this.tryLock()) {
            printf(CURRENT_LOCK + "get lock success");
            return;
        }
        try {
            waitForLock(WAIT_LOCK);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void waitForLock(String path) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(path, true);
        if (stat!=null){
            printf("wait "+ROOR_LOCK+path+"un lock");
            countDownLatch=new CountDownLatch(1);
            countDownLatch.await();
            printf(CURRENT_LOCK + "get lock success");
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {

        try {
            //创建一个临时有序的节点
            CURRENT_LOCK = zooKeeper.create(ROOR_LOCK + "/",
                    "0".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            printf("---" + CURRENT_LOCK + "try lock");
            //get children nodes
            List<String> childrens = zooKeeper.getChildren(ROOR_LOCK, false);
            SortedSet<String> sortedSet = new TreeSet<>();

            //sorted 对子节点排序
            childrens.forEach((s) -> {
                sortedSet.add(ROOR_LOCK + "/" + s);
            });
            //first node is min 最小的节点
            String first = sortedSet.first();

            if (CURRENT_LOCK.equals(first)) {
                // if the CURRENT_LOCK is min ,return true
                return true;
            }

            //set wait_lock

            SortedSet<String> lessThenMe = sortedSet.headSet(CURRENT_LOCK);

            if (!lessThenMe.isEmpty()) {
                WAIT_LOCK = lessThenMe.last();
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {


        return false;
    }

    @Override
    public void unlock() {

        printf("un lock");
        try {
            zooKeeper.delete(CURRENT_LOCK,-1);
            CURRENT_LOCK=null;
          //  zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (this.countDownLatch!=null){
            this.countDownLatch.countDown();
        }

    }

    public static void printf(String message) {
        System.out.printf("[current thread is %s]--%s\n", Thread.currentThread().getName(), message);
    }
}
