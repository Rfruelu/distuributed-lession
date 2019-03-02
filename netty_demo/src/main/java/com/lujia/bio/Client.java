package com.lujia.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * bio 客户端
 *
 * @author :lujia
 * @date :2018/11/1  10:10
 */
public class Client {

    private static String IP = "127.0.0.1";

    private static int DEFAULT_SERVER_PORT = 8888;


    public static void send(String request) {

        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket(IP, DEFAULT_SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            System.out.println("线程"+ Thread.currentThread().getName()+"收到服务端响应:" + in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }


    public static void main(String[] args) throws IOException {

        Random random = new Random();

        String[] arr = {"a", "b", "c"};
        int count = 100;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Socket socket = new Socket(IP, DEFAULT_SERVER_PORT);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {

                try {
                    countDownLatch.await();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(arr[random.nextInt(3)].getBytes());
                    /*outputStream.close();
                    in.close();
                    socket.close();*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            countDownLatch.countDown();
        }

    }
}
