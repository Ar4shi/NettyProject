package com.io.serverDemo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Worker implements Runnable {

    private Thread thread;
    private Selector selector;
    private String name;
    private volatile boolean start = false;

    private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

    public Worker(String name) {
        this.name = name;
    }

    public void register(SocketChannel socketChannel) throws IOException {
        if (!start) {
            selector = Selector.open();
            thread = new Thread(this, name);
            thread.start();
            start = true;
        }
        // 向队列添加任务，但这个任务并未执行
        queue.add(() -> {
            try {
                socketChannel.register(selector, SelectionKey.OP_READ, null);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        });
        // 唤醒
        selector.wakeup();
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select();
                Runnable task = queue.poll();
                if (task != null) {
                    task.run();
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.read(buffer);
                        buffer.flip();
                        System.out.println(Thread.currentThread().getName() + " result : ");
                        while (buffer.hasRemaining()) {
                            System.out.print((char) buffer.get());
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
