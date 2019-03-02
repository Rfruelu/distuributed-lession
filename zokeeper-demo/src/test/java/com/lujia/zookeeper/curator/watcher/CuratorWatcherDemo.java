package com.lujia.zookeeper.curator.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author :lujia
 * @date :2018/8/2  15:56
 */
public class CuratorWatcherDemo {

    public static void main(String[] args) {

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("192.168.128.128:2181,192.168.128.129:2181,192.168.128.130:2181")
                .connectionTimeoutMs(4000)
                .sessionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("curator")
                .build();


        try {
            curatorFramework.start();

            //this listener is for /zk-curator children 只是针对/zk-curator的下面的子节点
            //创建、删除、更新
            //addListenerWithPathChildCache(curatorFramework,"/zk-curator");

            //只会监听/zk-curator节点的变化，不会监听子节点（创建和更新，不会监听删除事件）
            //addListenerWithNodeCache(curatorFramework,"/zk-curator");

            //
            addListenerWithTreeCache(curatorFramework,"/zk-curator");

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * PathChildCache 监听一个节点下子节点的创建、删除、更新
     * NodeCache  监听一个节点的更新和创建事件
     * TreeCache  综合PatchChildCache和NodeCache的特性
     */


    /**
     *   add a listener with pathChildrenCache
     *   添加一个pathChildrenCache 监听 ，监听一个节点下子节点的创建、删除、更新
     *
     * @param curatorFramework
     * @param path
     */
    public static void addListenerWithPathChildCache(CuratorFramework curatorFramework,String path) throws Exception {

        PathChildrenCache pathChildrenCache=new PathChildrenCache(curatorFramework,path,true);

        PathChildrenCacheListener pathChildrenCacheListener =new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("event type:"+pathChildrenCacheEvent.getType());
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);

        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);

    }

    /**
     * add a listener with nodeCache
     * 添加一个针对nodeCache的监听事件
     * @param curatorFramework
     * @param path
     */
    public static void addListenerWithNodeCache(CuratorFramework curatorFramework,String path) throws Exception {
        NodeCache nodeCache=new NodeCache(curatorFramework,path,false);

        NodeCacheListener nodeCacheListener=new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Receive Event"+nodeCache.getCurrentData().getPath());
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();

    }


    /**
     * add a listener with treeCache
     * 添加一个treeCache监听  针对节点以及节点的子节点
     * @param curatorFramework
     * @param path
     */
    public static void addListenerWithTreeCache(CuratorFramework curatorFramework,String path) throws Exception {
        TreeCache treeCache=new TreeCache(curatorFramework,path);

        /*TreeCacheListener treeCacheListener=new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                System.out.println("receive Event:"+treeCacheEvent.getData().getPath()+"---event type:"+treeCacheEvent.getType());
            }
        };*/
        //use lamda
        treeCache.getListenable().addListener((CuratorFramework client, TreeCacheEvent treeCacheEvent)->{
            System.out.println("receive Event:"+treeCacheEvent.getData().getPath()+"---event type:"+treeCacheEvent.getType());
        });

        treeCache.start();
    }

}
