package com.io.serverDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
    public static void main(String[] args) throws IOException {
        // 1. 创建 selector , 管理多个channel
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 非阻塞模式运行
        ssc.configureBlocking(false);
        // 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));
        // 2. 建立 selector 和 channel 的联系
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // 服务器key只关注 accept 事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        while (true) {
            // 3. select 方法，没有事情发生，线程阻塞，有事件发生 线程恢复
            // select 事件未处理时，它不会阻塞，所以事件发生后 要么处理，要么取消，不能置之不理
            selector.select();
            // 4. 处理事件, selectedKeys 内部包含了所有【发生】的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 处理key时，要从集合中移除，不然下次处理由于【发生】的事件已经处理，会造成npe
                iterator.remove();
                System.out.println("key :  " + key);
                if (key.isAcceptable()) {

                } else if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = channel.read(buffer);
                        if (read == -1) {

                        } else {
                            buffer.flip();
                            while (buffer.hasRemaining()) {
                                System.out.println((char) buffer.get());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();
                    }
                }
            }
        }
    }
}
