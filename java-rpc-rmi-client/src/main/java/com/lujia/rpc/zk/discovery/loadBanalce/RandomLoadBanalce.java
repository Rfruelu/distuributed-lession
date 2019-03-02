package com.lujia.rpc.zk.discovery.loadBanalce;

import java.util.List;
import java.util.Random;

/**
 * @author :lujia
 * @date :2018/8/6  21:36
 */
public class RandomLoadBanalce extends AbstractLoadBanalce{


    @Override
    public String doSelect(List<String> list) {
        int size = list.size();
        Random random=new Random();

        return list.get(random.nextInt(size));
    }
}
