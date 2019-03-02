package com.lujia.soap;

import com.lujia.soap.dubbo.service.DemoProtocolService;
import com.lujia.soap.dubbo.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("dubbo-client.xml");
     /*   Protocol protocol= ExtensionLoader.getExtensionLoader(Protocol.class)
                .getExtension("myProtocol");
        System.out.println(protocol.getDefaultPort());*/


        HelloService helloService = (HelloService) context.getBean("myHelloService");
        helloService.say("lujia");



        DemoProtocolService demoProtocolService = (DemoProtocolService) context.getBean("demoProtocolService");
        String protocol = demoProtocolService.getProtocol();
        System.out.println(protocol);
    }
}
