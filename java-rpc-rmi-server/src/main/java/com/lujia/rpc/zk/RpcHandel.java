package com.lujia.rpc.zk;

import com.lujia.rpc.demo.domain.RpcRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author :lujia
 * @date :2018/7/24  16:38
 */
public class RpcHandel implements Runnable {


    private Socket socket;
    private Map<String ,Object> map;

    public RpcHandel(Socket socket, Map<String ,Object> map) {
        this.socket = socket;
        this.map = map;
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

        String className = request.getClassName();

        String methodName = request.getMethodName();

        String version = request.getVersion();
        if (null!=version&&!"".equals(version)){
            className=className+"-"+version;
        }

        Object object = map.get(className);

        Method method= object.getClass().getMethod(methodName,tClass);

        Object invoke = method.invoke(object, parameters);
        return invoke;

    }


}
