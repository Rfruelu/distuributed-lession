package com.lujia.rpc.demo.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author :lujia
 * @date :2018/7/24  17:30
 */
public class RpcRegisty {

    private static final ExecutorService executorService= Executors.newCachedThreadPool();

    public  void createReisty(final int port,final Object service){
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new RpcHandel(socket,service));
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
