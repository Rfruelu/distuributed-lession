package com.lujia;

import com.lujia.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author :lujia
 * @date :2019/2/12  10:21
 */
public class CopyTest {

    @Test
    public void test(){
        List<User> list=new ArrayList<>(10);

        User user=new User();
        user.setName("lujia");
        User user1=new User();
        user1.setName("luxin");
        list.add(user);
        list.add(user1);


        user.setName("111");
/*
        list.forEach(user2 ->{
            System.out.println(user2.getName());
        } );*/
        User users[]=new User[]{user,user1};

        User[] users1 = Arrays.copyOf(users, users.length * 2 + 1);

        User userTemp=users1[1];
        userTemp.setName("2222");


        System.out.println(userTemp==user1);
        Arrays.asList(users).forEach(userForEach->{
            System.out.println(userForEach.getName());
        });

    }


    @Test
    public void testContains(){
        System.out.println("".contains(""));
    }


    @Test
    public void addTest(){
        List<String > list=new ArrayList<>(10);
        list.add("1");
        list.add("2");
        list.add("3");

        for (String s : list) {
            list.add("4");
        }

        list.stream().forEach(System.out::println);
    }
}
