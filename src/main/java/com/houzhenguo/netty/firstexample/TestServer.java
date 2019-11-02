package com.houzhenguo.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
/**
 *  20191027
 *  handler added
 * channel registered
 * channel active
 * 执行 read0
 * 请求方法名GET
 * channel in active
 * channel un registered
 */
public class TestServer {
    public static void main(String[] args) throws Exception{
        // 就是两个死循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();  // 事件循环组 两个线程组 boss接收链接，交给worker
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 真正完成

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();// 用于启动服务端的一个类
            serverBootstrap.group(bossGroup, workerGroup)// acceptor boss / worker
                    .channel(NioServerSocketChannel.class) // 反射实现
                    .childHandler(new TestServerInitlializer()); // 请求处理器（自定义的服务器初始化器）

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully(); // shutdown
            workerGroup.shutdownGracefully();
        }
    }
}
