package com.lujia.rpc.zk.discovery;

import com.lujia.rpc.zk.discovery.loadBanalce.LoadBanalce;
import com.lujia.rpc.zk.discovery.loadBanalce.RandomLoadBanalce;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :lujia
 * @date :2018/8/6  21:09
 */
public class DiscoveryServiceImpl implements DiscoveryService{


    volatile List<String> repository=new ArrayList<>();

    private CuratorFramework curatorFramework;

    public DiscoveryServiceImpl(String zkAddress) {
        curatorFramework= CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(2000,2))
                .sessionTimeoutMs(4000)
                .build();
        curatorFramework.start();


    }

    @Override
    public String discovery(String serviceName,String version) {
        try {

            String path ="/registry/"+serviceName;
            if (null!=version&&!"".equals(version)){
                path=path+"-"+version;
            }
            List<String> list=repository;
            List<String> pathList = curatorFramework.getChildren().forPath(path);
            repository= pathList;

            list.clear();
            //注册监听，动态修改节点的变化

            registryListener(path);
            LoadBanalce loadBanalce=new RandomLoadBanalce();
            return loadBanalce.loadBanalce(repository);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    private void registryListener(final  String path) throws Exception {

        PathChildrenCache pathChildrenCache=new PathChildrenCache(curatorFramework,
                path,true);

        PathChildrenCacheListener pathChildrenCacheListener=new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                List<String> list=repository;
                List<String> pathList = curatorFramework.getChildren().forPath(path);
                repository= pathList;

                list.clear();
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);


        pathChildrenCache.start();
    }


}
