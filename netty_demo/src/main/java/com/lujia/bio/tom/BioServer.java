package com.lujia.bio.tom;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author :lujia
 * @date :2018/11/2  15:52
 */
public class BioServer {


    private ServerSocket serverSocket;


    public BioServer(int port) {

        try {
            if (serverSocket==null){
                serverSocket=new ServerSocket(port);
            }
            System.out.println("服务端已经启动,端口是:"+port);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void lister(){


        try {

            while (true){
                Socket socket=serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte [] bytes=new byte[1024];
                int len = inputStream.read(bytes);
                if (len>0){
                    System.out.println("收到客户端消息:"+new String(bytes,0,len));
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    public static void main(String[] args) {
        new BioServer(9999).lister();
    }
}
