package com.lujia.soap;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * @author :lujia
 * @date :2018/8/9  22:18
 */
public class TestProtocol {

    public static void main(String[] args) {
        Protocol protocol= ExtensionLoader.getExtensionLoader(Protocol.class)
                .getExtension("myProtocol");
        System.out.println(protocol.getDefaultPort());
    }
}
