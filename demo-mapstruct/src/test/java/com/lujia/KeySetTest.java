package com.lujia;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author :lujia
 * @date :2019/2/11  10:06
 */
public class KeySetTest {

    @Test
    public void test(){
        Map<String,String> map= new HashMap<>(10);

        map.put("name","lujia");
        map.put("age","123");
        map.put("sex","男");
        Set<String> keySet = map.keySet();

        keySet.remove("age");

        /*keySet.forEach(key->{
            System.out.println("key"+key);
            System.out.println("value"+map.get(key));
        });*/


        map.forEach((key,value)->{
            System.out.println("key---:"+key);
            System.out.println("value---:"+value);
        });
    }


    @Test
    public void testInteger(){
        Integer integer=new Integer(10);

        integer=20;
        System.out.println(integer);
    }
}
