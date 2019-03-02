package com.lujia.soap.oracle;

import com.lujia.soap.spi.DataBaseDriver;

/**
 * @author :lujia
 * @date :2018/8/9  23:08
 */
public class OracleDriver implements DataBaseDriver {
    @Override
    public void connect() {
        System.out.println("oracle");
    }
}
