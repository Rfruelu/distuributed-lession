package com.jialu.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author :lujia
 * @date :2018/11/12  16:08
 */
public class BioServer {


    private static String ip = "127.0.0.1";
    private static int port = 7777;

    private static ServerSocket serverSocket;

    private static void start() {
        if (serverSocket != null) {
            return;
        }
        try {

            serverSocket = new ServerSocket(port);

            while (true) {
                Socket accept = serverSocket.accept();
                new Thread(new ServerHandler(accept)).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        start();

    }


}

class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        BufferedReader in = null;

        PrintWriter out = null;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(),true);
            String request;
            while (true) {

                if ((request=in.readLine())==null){
                    break;
                }
                System.out.println("server receive message:"+request);
                out.println("server send message:"+request.toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out!=null){
                out.close();
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
