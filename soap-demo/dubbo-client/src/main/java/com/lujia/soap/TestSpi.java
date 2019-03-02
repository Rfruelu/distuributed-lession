package com.lujia.soap;

import com.lujia.soap.spi.DataBaseDriver;

import java.util.ServiceLoader;

/**
 * @author :lujia
 * @date :2018/8/9  23:20
 */
public class TestSpi {

    public static void main(String[] args) {

        ServiceLoader<DataBaseDriver> serviceLoader=ServiceLoader.load(DataBaseDriver.class);

        for (DataBaseDriver dataBaseDriver:serviceLoader){
            dataBaseDriver.connect();
        }
    }
}
