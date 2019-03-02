package com.lujia.rpc.zk;

import com.lujia.rpc.zk.annotation.RpcAnnotation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author :lujia
 * @date :2018/8/6  22:11
 */
public class RpcServer {


    private static final ExecutorService executorService= Executors.newCachedThreadPool();

    private ZkRegistry zkRegistry;

    private String address;


    Map<String,Object> map=new HashMap<>();

    public RpcServer(ZkRegistry zkRegistry, String address) {
        this.zkRegistry = zkRegistry;
        this.address = address;
    }

    public void bind(Object ...objects){
        for (Object object : objects) {

            RpcAnnotation annotation = object.getClass().getAnnotation(RpcAnnotation.class);
            if (annotation!=null){
                String serviceName = annotation.value().getName();
                String version = annotation.version();
                if (null!=version&&!"".equals(version)){
                    serviceName=serviceName+"-"+version;
                }
                map.put(serviceName,object);
            }
        }
    }

    public void publisher(){
        ServerSocket serverSocket=null;

        try {

            String[] split = address.split(":");
            serverSocket=new ServerSocket(Integer.valueOf(split[1]));

            map.keySet().forEach((s)->{
                zkRegistry.registry(address,s);
            });
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new RpcHandel(socket,map));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
