package com.lujia.rpc.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author :lujia
 * @date :2018/8/6  22:26
 */
public class ZkRegistryImpl implements ZkRegistry {


    private CuratorFramework curatorFramework;


    public ZkRegistryImpl(String zkAddress) {
        curatorFramework= CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(2000,2))
                .sessionTimeoutMs(4000)
                .build();
        curatorFramework.start();
    }

    @Override
    public void registry(String address, String serviceName) {

        String path="/registry/"+serviceName;
        try {
            if (curatorFramework.checkExists().forPath(path)==null){
                curatorFramework.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(path,"0".getBytes());

            }

            String addressPath=path+"/"+address;
            String znode = curatorFramework.create()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(addressPath, "0".getBytes());
            System.out.println("--服务注册成功--"+znode);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
