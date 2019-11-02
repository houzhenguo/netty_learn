package com.houzhenguo.netty.thirdexample;

import com.houzhenguo.netty.secondexample.MyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MyChatClient {
    public static void main(String[] args) throws Exception{
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap(); // client与server不同
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                    .handler(new MyChatClientInitializer()); // child handler
            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();

            // 死循环读取
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (;;) {
                channel.writeAndFlush(br.readLine() +"\r\n");
            }

        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
