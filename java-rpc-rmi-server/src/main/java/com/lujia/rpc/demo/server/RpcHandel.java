package com.lujia.rpc.demo.server;

import com.lujia.rpc.demo.domain.RpcRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author :lujia
 * @date :2018/7/24  16:38
 */
public class RpcHandel implements Runnable {


    private Socket socket;
    private Object service;

    public RpcHandel(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream=null;
        try {

            InputStream inputStream = socket.getInputStream();
            objectInputStream=new ObjectInputStream(inputStream);
            RpcRequest request= (RpcRequest) objectInputStream.readObject();
            Object user=invoke(request);

            OutputStream outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private Object invoke(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Object[] parameters = request.getParameters();

        Class<?> []tClass=new Class[parameters.length];
        for (int i=0;i<parameters.length;i++) {

            tClass[i]=parameters[i].getClass();
        }

        String methodName = request.getMethodName();

        Method method=service.getClass().getMethod(methodName,tClass);

        Object invoke = method.invoke(service, parameters);
        return invoke;

    }


}
