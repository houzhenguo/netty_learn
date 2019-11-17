package com.houzhenguo.netty.sixthexample;

import com.houzhenguo.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) {
       // System.out.println(msg.toString());

        MyDataInfo.MyMessage.DataType type = msg.getDataType();
        if (type == MyDataInfo.MyMessage.DataType.PersonType) {
            MyDataInfo.Person person = msg.getPerson();
            System.out.println("person"+ person);
        }else if(type == MyDataInfo.MyMessage.DataType.DogType){
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println("dog" + dog); // 分别打出属性值，就可以输出汉字。
        }else {
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println("cat" + cat);
        }

    }
}
