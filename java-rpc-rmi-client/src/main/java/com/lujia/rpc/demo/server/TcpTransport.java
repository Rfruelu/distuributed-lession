package com.lujia.rpc.demo.server;

import com.lujia.rpc.demo.domain.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author :lujia
 * @date :2018/7/24  18:15
 */
public class TcpTransport {

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public TcpTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket(){
        Socket socket=null;

        try {
             socket=new Socket(host,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }


    public Object send(RpcRequest request){

        ObjectOutputStream objectOutputStream =null;
        ObjectInputStream objectInputStream=null;
        Object o=null;
        Socket socket=null;
        try {
            socket=newSocket();
            OutputStream outputStream = socket.getOutputStream();
            objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(request);

            objectOutputStream.flush();
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            try {
                o = objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                objectInputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return o;
    }
}
