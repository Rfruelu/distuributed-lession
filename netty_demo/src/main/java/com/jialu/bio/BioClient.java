package com.jialu.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * @author :lujia
 * @date :2018/11/12  16:44
 */
public class BioClient {

    private static String ip="127.0.0.1";
    private static int port=7777;

    private static void send(String message){
        Socket socket=null;
        BufferedReader in = null;

        PrintWriter out = null;

        try {

            socket=new Socket(ip,port);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream(),true);

            out.println(message);
            String response ;
            if ((response=in.readLine())!=null){
                System.out.println("receive from server "+response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String[] arr={"a","b","c","e"};
        Random random=new Random();

        for (int i=0;i<10;i++){
            String message= arr[random.nextInt(3)];
            new Thread(()->{
                send(message);
            }).start();

        }


    }
}
