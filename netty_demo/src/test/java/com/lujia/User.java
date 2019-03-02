package com.lujia;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author :lujia
 * @date :2018/11/14  10:43
 */
@Data
@Slf4j
@NoArgsConstructor
public class User {


    private String name;
    private int age;


    @Test
    public void test(){
        User user=new User();
        user.setAge(22);
       log.info(user.getAge()+"");
    }
}
