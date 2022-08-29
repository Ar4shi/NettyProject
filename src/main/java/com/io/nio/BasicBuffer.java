package com.io.nio;

import java.nio.IntBuffer;

/**
 * @author jianghan
 * @date 2022-03-08 03:28
 */
public class BasicBuffer {

    public static void main(String[] args) {

        // 创建一个buffer 大小为5
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer中存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i << 1);
        }

        //要想从buffer读取数据 ，先要将buffer读写切换
        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(4);

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }

}
