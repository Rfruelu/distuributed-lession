package com.lujia.rpc.zk.discovery;

/**
 * @author :lujia
 * @date :2018/8/6  21:08
 */
public interface DiscoveryService {

    String discovery(String serviceName,String verison);
}
