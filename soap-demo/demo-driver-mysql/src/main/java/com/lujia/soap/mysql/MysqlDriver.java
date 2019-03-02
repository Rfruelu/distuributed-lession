package com.lujia.soap.mysql;

import com.lujia.soap.spi.DataBaseDriver;

/**
 * @author :lujia
 * @date :2018/8/9  23:03
 */
public class MysqlDriver implements DataBaseDriver {
    @Override
    public void connect() {
        System.out.println("mysql");
    }
}
