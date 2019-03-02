package com.lujia.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

/**
 * @author :lujia
 * @date :2018/11/8  15:00
 */
public class NettyServerDemo {


    private static String IP="127.0.0.1";
    private static int por =9999;

    //主线程组
    private static EventLoopGroup bossGroup=new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()*2);
    //worker 组
    private static EventLoopGroup workerGroup=new NioEventLoopGroup(100);


    public static void start() throws Exception{

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                        pipeline.addLast(new StringDecoder(Charset.defaultCharset()));
                        pipeline.addLast(new StringEncoder(Charset.defaultCharset()));
                        pipeline.addLast(new NettyServerHandler());
                    }
                });

        //绑定ip端口监听
        ChannelFuture channelFuture = serverBootstrap.bind(IP, por).sync();
        channelFuture.channel().closeFuture().sync();
        System.out.println("netty server start success");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("服务端开始启动----->");
        NettyServerDemo.start();
    }
}
