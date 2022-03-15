package com.ar4shi.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO file 通道读/写
 *
 * @author jianghan
 * @date 2022-03-16 02:38
 */
public class NIOFileChannelReadAndWrite {

    private static final String FILE_URL = "./src/main/java/com/ar4shi/nio/resources/helloarashi.txt";

    private static final String FILE_URL_COPY = "./src/main/java/com/ar4shi/nio/resources/helloarashiCopy.txt";

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(FILE_URL);
        FileChannel channel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream(FILE_URL_COPY);
        FileChannel channel02 = fileOutputStream.getChannel();

        // 利用一个缓冲区双向读写
        ByteBuffer buffer = ByteBuffer.allocate(512);
        for (;;){
            // 复位
            buffer.clear();
            int read = channel01.read(buffer);
            if(read == -1){// 读完
                break;
            }
            // 将读入的数据直接写入到 channel02 中
            buffer.flip();
            channel02.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
