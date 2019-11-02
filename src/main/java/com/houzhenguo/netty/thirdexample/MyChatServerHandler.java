package com.houzhenguo.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 存放channel group
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        final Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch.equals(channel)) {
                channel.writeAndFlush("自己："+msg+"\n");
            }else {
                ch.writeAndFlush(ch.remoteAddress()+"say:"+msg+"\n");
            }
        });
    }

    @Override // 服务器与客户端建立链接
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel(); // 相当于 connection
        // broadcast
        channelGroup.writeAndFlush("[服务器] - "+ channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);
    }

    @Override // connection 断掉
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // remove broadcast
        channelGroup.writeAndFlush("[服务器] - "+ channel.remoteAddress()+"离开\n");
        // channelGroup.remove(channel);// 链接断掉之后，netty自动会移除channel group 这行没必要

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +"上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +"下线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
