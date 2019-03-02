package com.lujia.rpc.demo.domain;

import java.io.Serializable;

/**
 * @author :lujia
 * @date :2018/7/24  16:17
 */
public class User implements Serializable{


    private static final long serialVersionUID = -8209526787288512525L;
    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
