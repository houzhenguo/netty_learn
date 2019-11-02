package com.houzhenguo.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 *  自己定义的处理器
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override // 读取客户端发过来的请求，响应客户端的方法 相当于messageReceived mina
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress()); //获取远程的地址
        if (msg instanceof HttpRequest) {
            System.out.println("执行 read0");
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("请求方法名"+ httpRequest.method().name());

            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求favicon");
                return;
            }

            ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8); // 返回的内容
            // http 版本 ，ok，内容
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response); // 返回
            ctx.close(); //这样就会关闭链接 这样判断不是严谨，需要判断HTTP版本
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }

    /**
     *  inactive 与 unregistered 有keep alive,所以可能只有关闭浏览器或者 长时间才会调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel in active");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel un registered");
        super.channelUnregistered(ctx);
    }
}
