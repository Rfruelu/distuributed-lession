package com.lujia.rpc.demo.server;

import com.lujia.rpc.demo.domain.User;

/**
 * @author :lujia
 * @date :2018/7/24  16:35
 */
public class UserServiceImpl implements  UserService{
    @Override
    public User getUser(String id) {
        User user =new User();
        user.setId(id);
        user.setName("lujia");

        return user;
    }
}
