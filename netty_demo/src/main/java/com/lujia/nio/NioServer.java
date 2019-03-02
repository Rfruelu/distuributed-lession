package com.lujia.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author :lujia
 * @date :2018/11/5  10:11
 */
public class NioServer {

    private static class TCPServer implements Runnable{


        private InetSocketAddress inetSocketAddress;

        public TCPServer(InetSocketAddress inetSocketAddress) {
            this.inetSocketAddress = inetSocketAddress;
        }

        @Override
        public void run() {

            /**
             * 服务端通道
             */
            ServerSocketChannel serverSocketChannel=null;

            /**
             * 选择器
             */
            Selector selector=null;

            Random random=new Random();

            try {
                //创建一个选择器
                selector=Selector.open();
                //创建服务端通道
                serverSocketChannel=ServerSocketChannel.open();
                //设置非阻塞方式
                serverSocketChannel.configureBlocking(false);
                /**
                 * 设置服务器监听端口，100：设置最大连接缓冲数
                 */
                serverSocketChannel.bind(inetSocketAddress,100);

                //在selector上注册事件 ACCEPT：连接事件
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("服务器启动："+inetSocketAddress);

            try {

                Charset charset=Charset.defaultCharset();

                while (!Thread.currentThread().isInterrupted()){

                    int status = selector.select();
                    if (status==0){
                        continue;
                    }
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    SelectionKey selectionKey=null;
                    while (iterator.hasNext()){
                        selectionKey= iterator.next();


                        iterator.remove();
                        try {
                            if (selectionKey.isAcceptable()){
                                //处理连接事件
                                SocketChannel socketChannel = serverSocketChannel.accept();
                                //设置通道是非阻塞的
                                socketChannel.configureBlocking(false);
                                //在选择器上注册通道的读事件
                                socketChannel.register(selector, SelectionKey.OP_READ, new Buffers(256,256));
                                System.out.println("accept from "+ socketChannel.getRemoteAddress());
                            }
                            if (selectionKey.isReadable()){
                                //读事件
                                //获得通道对应的缓冲区
                                Buffers buffers = (Buffers)selectionKey.attachment();
                                ByteBuffer readBuffer = buffers.getReadBuffer();
                                ByteBuffer writeBuffer = buffers.getWriteBuffer();
                                //获得对应的channel
                                SocketChannel channel = (SocketChannel)selectionKey.channel();
                                channel.read(readBuffer);
                                readBuffer.flip();

                                CharBuffer charBuffer = charset.decode(readBuffer);

                                System.out.println(charBuffer.array());
                                readBuffer.rewind();

                                writeBuffer.put("echo from service:".getBytes("UTF-8"));
                                writeBuffer.put(readBuffer);
                                readBuffer.clear();

                                selectionKey.interestOps(selectionKey.interestOps()|SelectionKey.OP_WRITE);
                            }

                            if (selectionKey.isWritable()){
                                //写事件
                                //获得通道对应的缓冲区
                                Buffers buffers = (Buffers)selectionKey.attachment();
                                ByteBuffer writeBuffer = buffers.getWriteBuffer();

                                writeBuffer.flip();
                                SocketChannel channel = (SocketChannel)selectionKey.channel();


                                int len=0;
                                while (writeBuffer.hasRemaining()){
                                    len=channel.write(writeBuffer);
                                    if (len==0){
                                        break;
                                    }
                                }
                                writeBuffer.compact();
                                if (len!=0){
                                    //不等于0，说明书已经全部写入socket缓冲区，取消写事件
                                    selectionKey.interestOps(selectionKey.interestOps()&(~SelectionKey.OP_WRITE));
                                }

                            }
                        }catch (IOException e){
                            e.printStackTrace();
                            //客户端连接出现异常，就移除这个key
                            selectionKey.cancel();
                            selectionKey.channel().close();
                        }

                        Thread.sleep(random.nextInt(500));
                        //移除这个key
                      //  iterator.remove();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Thread thread=new Thread(new TCPServer(new InetSocketAddress(9999)));
        thread.start();
        //main线程休眠 10000000毫秒，让thread 运行这么长时间
        Thread.sleep(1000000);

        //中断线程
        thread.interrupt();
    }
}
