package com.lujia.QQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 聊天小程序服务端
 *
 * @author :lujia
 * @date :2018/11/3  10:05
 */
public class QQServer {

    private static Map<String, Socket> map = new ConcurrentHashMap<>();


    private static class ServerHandler implements Runnable {

        private Socket client;

        public ServerHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            try {

                ///Scanner scanner=new Scanner(client.getInputStream());

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String message;

                while (true) {

                    //if (scanner.hasNextLine()) {
                    if ((message = in.readLine()) != null) {
                        // message= scanner.nextLine();
                        if (message.startsWith("userName")) {
                            String[] split = message.split(":");
                            registerUser(split[1], client);

                        } else if (message.startsWith("G")) {
                            message = message.split(":")[1];
                            groupTalk(message, client);
                        } else if (message.startsWith("P")) {
                            String userName = message.split(":")[1].split("-")[0];
                            message = message.split(":")[1].split("-")[1];
                            privateTalk(userName, message, client);
                        } else if (message.startsWith("O")) {
                            out(client);
                        } else {
                            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                            out.println("消息格式错误");
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private void out( Socket client) {
            try {
                String userName="";
                Set<String> keySet = map.keySet();
                for (String s : keySet) {
                    if (map.get(s).equals(client)){
                        userName=s;
                    }
                }
                if (userName!=null&&userName!=""){

                    System.out.println("用户"+userName+"下线了");
                    map.remove(userName);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void privateTalk(String userName, String message, Socket client) {
            try {

                Socket socket = map.get(userName);
                PrintWriter out = null;
                if (socket != null) {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("收到端口号:" + client.getPort() + "发来的私聊消息" + message);
                } else {
                    out = new PrintWriter(client.getOutputStream(), true);
                    out.println("用户:" + userName + "未上线");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void groupTalk(String message, Socket client) {

            try {
                Set<Map.Entry<String, Socket>> entries = map.entrySet();

                for (Map.Entry<String, Socket> entry : entries) {
                    Socket value = entry.getValue();
                    PrintWriter out = new PrintWriter(value.getOutputStream(), true);
                    out.println("收到端口号:" + client.getPort() + "发来的消息" + message);
                    out.close();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void registerUser(String name, Socket client) {

            System.out.println("用户:" + name + "上线");
            map.put(name, client);
            PrintWriter out = null;
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                System.out.println("当前在线人数:" + map.size());
                out.println("用户注册成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            System.out.println("等待客户端连接");
            Socket accept = serverSocket.accept();
            System.out.println("有客户端连接上端口号:" + accept.getPort());
            executorService.submit(new ServerHandler(accept));
        }

    }
}
