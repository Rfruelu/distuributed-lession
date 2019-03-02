package com.lujia.soap;

import com.lujia.soap.dubbo.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author :lujia
 * @date :2018/8/9  23:38
 */
public class TestCluster {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=
                new ClassPathXmlApplicationContext("dubbo-client.xml");

        for (int i=0;i<10;i++){

            UserService userService=(UserService) context.getBean("userService");
            String userName = userService.getUserName();
            System.out.println(userName);
        }

    }
}
