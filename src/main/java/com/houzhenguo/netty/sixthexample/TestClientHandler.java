package com.houzhenguo.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) {

    }

    /**
     *  client to server send diff msg
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        int randomInt = new Random().nextInt(3); // 0 1 2
        MyDataInfo.MyMessage myMessage = null;
        if (randomInt == 0) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                    .setPerson(MyDataInfo.Person.newBuilder()
                    .setName("张三")
                    .setAge(22)
                    .setAddress("北京市").build()).build();
        }else if(randomInt == 1) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder()
                            .setName("一只狗")
                            .setAge(22)
                            .build()).build();
        }else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder()
                            .setName("一只猫")
                            .setCity("天津市").build()).build();
        }

        ctx.channel().writeAndFlush(myMessage);
    }
}
