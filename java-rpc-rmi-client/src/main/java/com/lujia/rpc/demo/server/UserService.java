package com.lujia.rpc.demo.server;

import com.lujia.rpc.demo.domain.User;

/**
 * @author :lujia
 * @date :2018/7/24  16:35
 */
public interface UserService {

    User getUser(String id);
}
