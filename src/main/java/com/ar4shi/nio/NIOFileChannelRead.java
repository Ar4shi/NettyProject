package com.ar4shi.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO file 通道读
 *
 * @author jianghan
 * @date 2022-03-16 02:15
 */
public class NIOFileChannelRead {

    private static final String FILE_URL = "./src/main/java/com/ar4shi/nio/resources/helloarashi.txt";

    public static void main(String[] args) throws Exception {

        // 创建文件的输入流
        File file = new File(FILE_URL);
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

        // 将通道的数据读入到buffer
        channel.read(buffer);

        // 此处不需要 buffer.flip(); 因为没有对buffer做读写转换的需求

        System.out.println(new String(buffer.array()));
        fileInputStream.close();
    }
}
