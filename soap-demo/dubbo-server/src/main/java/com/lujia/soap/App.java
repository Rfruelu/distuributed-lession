package com.lujia.soap;

import com.alibaba.dubbo.container.Main;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //默认情况下会使用spring容器来启动服务
        Main.main(new String[]{"spring","log4j"});
    }
}
