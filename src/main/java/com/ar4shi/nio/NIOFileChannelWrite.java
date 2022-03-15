package com.ar4shi.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO file 通道写
 *
 * @author jianghan
 * @date 2022-03-16 01:59
 */
public class NIOFileChannelWrite {

    private static final String FILE_URL = "./src/main/java/com/ar4shi/nio/resources/helloarashi.txt";

    public static void main(String[] args) throws Exception {
        String str = "hello,Arashi";
        // 创建一个文件输出流 ->channel
        // 相对路径的起始路径不是.java文件所在的目录而是src文件所在的目录
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_URL);

        // 通过fileOutputStream 获取(初始化)对应的 FileChannel
        // 此fileChannel的真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        // 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 将Str放入缓冲区
        buffer.put(str.getBytes());

        // 输入输出反转
        buffer.flip();

        // 将buffer中的数据写入到channel
        fileChannel.write(buffer);
        fileOutputStream.close();
    }
}
