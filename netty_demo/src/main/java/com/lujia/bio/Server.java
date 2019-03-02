package com.lujia.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * bio 服务端
 * @author :lujia
 * @date :2018/11/1  9:31
 */
public class Server {

    private static int DEFAULT_SERVER_PORT=8888;

    private static ServerSocket serverSocket;
    public static void start(){
        if (serverSocket!=null) {
            return;
        }
        try {
            serverSocket=new ServerSocket(DEFAULT_SERVER_PORT);
            System.out.println("线程"+Thread.currentThread().getName()+"服务端启动成功--");
            while (true){
                Socket accept = serverSocket.accept();
                new Thread(new ServerHandler(accept)).start();
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
                serverSocket=null;
            }
        }
    }


}
