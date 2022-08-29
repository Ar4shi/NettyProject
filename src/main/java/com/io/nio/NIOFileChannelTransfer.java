package com.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author jianghan
 * @date 2022-03-16 02:50
 */
public class NIOFileChannelTransfer {

    private static final String FILE_URL = "./src/main/java/com/ar4shi/nio/resources/helloarashi.txt";

    private static final String FILE_URL_COPY_02 = "./src/main/java/com/ar4shi/nio/resources/helloarashiCopy02.txt";

    public static void main(String[] args) throws Exception{

        FileInputStream fileInputStream = new FileInputStream(FILE_URL);
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_URL_COPY_02);

        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel targetCahnnel = fileOutputStream.getChannel();

        // 使用transferFrom进行拷贝
        targetCahnnel.transferFrom(sourceChannel,0,sourceChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
