package com.lujia.rpc.zk.discovery.loadBanalce;

import java.util.List;

/**
 * @author :lujia
 * @date :2018/8/6  21:41
 */
public abstract class AbstractLoadBanalce implements LoadBanalce {


    @Override
    public String loadBanalce(List<String> list) {

        if (null==list||list.isEmpty()){
            return null;

        }

        if (list.size()==1){
            return list.get(0);
        }
        return doSelect(list);
    }

    public abstract String doSelect(List<String> list);
}
