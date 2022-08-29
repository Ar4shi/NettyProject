package com.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * NIO的分散和聚合
 * <p>
 * Scattering 将数据写入到buffer时，可以采用buffer数组，依次写入
 * Gathering 从buffer读取数据时，可以采用buffer数组，依次读出
 *
 * @author jianghan
 * @date 2022-03-18 03:11
 */
public class ScatteringAndGathering {

    public static void main(String[] args) throws Exception {

        //使用serverSocketChannel 和 socketChannel

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // 绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(5);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println("连接成功...");
        // 假定从客户端接受10个字节
        int messageLength = 10;

        for (; ; ) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += 1;
                System.out.println("byteRead = " + byteRead);
                // 打印
                Arrays.asList(byteBuffers).stream()
                        .map(byteBuffer -> "postion=" + byteBuffer.position() + " ,limit = " + byteBuffer.limit())
                        .forEach(System.out::println);
            }
            // 将所有buffer进行flip
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);

            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += 1;
            }

            // 将所有buffer clear
            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);
            // 打印
            Arrays.asList(byteBuffers).stream()
                    .map(byteBuffer -> "postion=" + byteBuffer.position() + " ,limit = " + byteBuffer.limit())
                    .forEach(System.out::println);
        }
    }
}
