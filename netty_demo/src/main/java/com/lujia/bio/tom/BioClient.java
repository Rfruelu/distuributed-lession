package com.lujia.bio.tom;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

/**
 * @author :lujia
 * @date :2018/11/2  16:11
 */
public class BioClient {

    public static void main(String[] args) {

        try {


            int count=100;
            //CountDownLatch countDownLatch=new CountDownLatch(count);
            for (int i = 0; i < count; i++) {
                new Thread(()->{
                   /* try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    try {
                        Socket socket=new Socket("127.0.0.1",9999);
                        OutputStream outputStream = socket.getOutputStream();

                        outputStream.write(UUID.randomUUID().toString().getBytes());
                        outputStream.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                //countDownLatch.countDown();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
