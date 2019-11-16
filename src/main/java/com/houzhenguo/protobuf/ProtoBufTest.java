package com.houzhenguo.protobuf;

public class ProtoBufTest {
    public static void main(String[] args) throws Exception{
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("houzhenguo")
                .setAge(20)
                .setAddress("beijing").build();

        // 转换成字节数组
        byte[] student2byteArray = student.toByteArray();
        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2byteArray);
        System.out.println(student1);
    }
}
