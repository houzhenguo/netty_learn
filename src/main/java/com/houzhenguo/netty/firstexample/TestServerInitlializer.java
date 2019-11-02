package com.houzhenguo.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
// 20191027 2
/**
 *  链接建立之后就会被创建，就会执行 initChannel方法
 */
public class TestServerInitlializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline(); // 管道，可以有很多channel handler 相当于拦截器
        // HttpRequestDecoder HttpResponseEncoder
        pipeline.addLast("httpServerCodec",new HttpServerCodec()); // netty提供 可以自己提供处理器 编解码用的
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler()); // 自定义的handler
    }
}
