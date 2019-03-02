package com.lujia.QQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author :lujia
 * @date :2018/11/3  11:01
 */
public class QQClient {


    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 9999);
        new Thread(new FromServerQQ(socket)).start();
        new Thread(new ToServerQQ(socket)).start();
    }

}

class FromServerQQ implements Runnable {

    private Socket socket;

    public FromServerQQ(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in= null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                String message;
                if ((message=in.readLine())!=null){

                    System.out.println(message);
                }

                if (socket.isClosed()){
                    System.out.println("客户端关闭。。。");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ToServerQQ implements Runnable {
    private Socket socket;

    public ToServerQQ(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                System.out.println("请输入信息");
                String message;
                if (scanner.hasNext()) {
                    message = scanner.nextLine();
                    out.println(message);
                    //客户端退出标志：B
                    if (message.startsWith("B")) {
                        System.out.println("客户端退出。。。");
                        scanner.close();
                        out.close();
                        socket.close();
                        break;
                    }
                }

            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

