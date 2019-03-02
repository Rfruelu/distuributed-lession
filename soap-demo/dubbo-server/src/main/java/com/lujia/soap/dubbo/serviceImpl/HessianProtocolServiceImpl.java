package com.lujia.soap.dubbo.serviceImpl;

import com.lujia.soap.dubbo.service.DemoProtocolService;

/**
 * @author :lujia
 * @date :2018/8/9  21:59
 */
public class HessianProtocolServiceImpl implements DemoProtocolService {
    @Override
    public String getProtocol() {
        System.out.println("hessian");
        return "hessian";
    }
}
