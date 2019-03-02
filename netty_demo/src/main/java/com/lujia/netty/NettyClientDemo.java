package com.lujia.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author :lujia
 * @date :2018/11/8  15:38
 */
public class NettyClientDemo  implements Runnable{


    @Override
    public void run() {

        EventLoopGroup group=new NioEventLoopGroup();

        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                           /* pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast(new StringEncoder(Charset.defaultCharset()));
                            pipeline.addLast(new StringDecoder(Charset.defaultCharset()));
                            pipeline.addLast(new NettyClientHandler());*/

                            pipeline.addLast("frameDecoder",
                                    new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast("handler", new NettyClientHandler());
                        }
                    });

                for (int i=0;i<10;i++){
                    ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).sync();
                    channelFuture.channel().writeAndFlush("client "+i);
                    channelFuture.channel().closeFuture().sync();
                }

        }catch(Exception e){

        }finally {
            group.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        new Thread(new NettyClientDemo(),"clientDemo").start();

    }
}
