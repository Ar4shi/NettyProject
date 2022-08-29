package com.io.nio;

import java.nio.ByteBuffer;

/**
 * @author jianghan
 * @date 2022-03-16 02:58
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(100);

        // 放入类型化的数据
        buffer.putInt(100);
        buffer.putLong(50L);
        buffer.putChar('A');

        //取出
        buffer.flip();

        // 输出也需要按照类型化的顺序
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
    }
}
