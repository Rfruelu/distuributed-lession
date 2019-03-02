package com.lujia.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author :lujia
 * @date :2018/12/20  20:46
 */
@Component
public class SpringHelper implements ApplicationContextAware {

    private static ApplicationContext APPLICATIONCONTEXT ;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        APPLICATIONCONTEXT=applicationContext;
    }

    public static Object getBean(String name){
        return APPLICATIONCONTEXT.getBean(name);
    }
    public static <T> T getBean(Class<T> tClass){
        return APPLICATIONCONTEXT.getBean(tClass);
    }
}
