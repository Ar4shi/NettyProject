package com.ar4shi.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接在堆外内存修改
 * 操作系统不需要拷贝一次
 *
 * @author jianghan
 * @date 2022-03-18 02:58
 */
public class MappedByteBufferTest {

    private static final String FILE_URL = "./src/main/java/com/ar4shi/nio/resources/helloarashi.txt";

    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile(FILE_URL, "rw");
        // 获取文件通道
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * 参数一：读写模式
         * 参数二：可以直接修改的起始位置
         * 参数三：映射到内存的大小，即将文件的多少个字节映射到内存
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');

        mappedByteBuffer.put(3, (byte) 'L');

        randomAccessFile.close();

        System.out.println("修改成功");
    }
}
