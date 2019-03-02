package com.lujia.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @author :lujia
 * @date :2018/11/5  17:13
 */
public class NioClient {


    private static class TcpClient implements Runnable{


        private InetSocketAddress inetSocketAddress;

        public TcpClient(InetSocketAddress inetSocketAddress) {
            this.inetSocketAddress = inetSocketAddress;
        }

        private Random random=new Random();

        @Override
        public void run() {

            Charset charset=Charset.defaultCharset();

            Selector selector=null;

            try {

                selector=Selector.open();

                SocketChannel socketChannel=SocketChannel.open();
                socketChannel.configureBlocking(false);

                int ops = SelectionKey.OP_WRITE | SelectionKey.OP_READ;

                socketChannel.register(selector,ops,new Buffers(256,256));

                socketChannel.connect(inetSocketAddress);

                while (!socketChannel.finishConnect()){
                    ;
                }
                System.out.println("连接已经完成");
            }catch (Exception e){
                e.printStackTrace();
            }

            try {

                int i=0;
                while (true){
                    //阻塞等待
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        Buffers buffers=(Buffers) key.attachment();

                        ByteBuffer readBuffer = buffers.getReadBuffer();
                        ByteBuffer writeBuffer = buffers.getWriteBuffer();

                        SocketChannel socketChannel=(SocketChannel) key.channel();

                        if (key.isReadable()){
                            socketChannel.read(readBuffer);
                            readBuffer.flip();
                            CharBuffer decode = charset.decode(readBuffer);
                            System.out.println(decode.array());
                            readBuffer.clear();
                        }

                        if (key.isWritable()){
                            writeBuffer.put(("name "+ i).getBytes());
                            writeBuffer.flip();
                            socketChannel.write(writeBuffer);
                            i++;
                            writeBuffer.clear();
                        }
                        Thread.sleep(1000+random.nextInt(1000));
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

    public static void main(String[] args) {

        Thread thread=new Thread(new TcpClient(new InetSocketAddress("127.0.0.1",9999)));
        thread.start();

    }
}
