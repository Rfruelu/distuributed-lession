package com.lujia.rpc.zk;

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

    private String path;


    public TcpTransport(String path) {
        this.path = path;
    }

    private Socket newSocket(){
        Socket socket=null;

        try {

            String[] arr = path.split(":");

            socket=new Socket(arr[0],Integer.valueOf(arr[1]));

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
