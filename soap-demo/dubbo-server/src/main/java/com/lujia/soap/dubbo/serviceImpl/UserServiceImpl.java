package com.lujia.soap.dubbo.serviceImpl;

import com.lujia.soap.dubbo.service.UserService;

/**
 * @author :lujia
 * @date :2018/8/9  23:29
 */
public class UserServiceImpl implements UserService {
    @Override
    public String getUserName() {
        return "lujia";
    }
}
