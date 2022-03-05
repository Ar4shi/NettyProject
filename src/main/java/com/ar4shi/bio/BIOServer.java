package com.ar4shi.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jianghan
 * @date 2022-03-05 19:00
 */
public class BIOServer {

    // 1、创建一个线程池
    // 2、如果有客户端连接，就创建一个线程，与之通讯
    public static void main(String[] args) throws Exception {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务起已启动...");

        while (true) {
            // 监听，等待客户端连接 未连接的话会阻塞
            System.out.println("等待连接...");
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            // 创建一个线程,与之通讯
            newCachedThreadPool.execute(() -> {
                // 与客户端通讯
                handleer(socket);
            });
        }
    }

    // 编写一个handler 和客户端通讯
    public static void handleer(Socket socket) {
        byte[] bytes = new byte[1024];
        // 通过socket 获取输入流
        try {
            System.out.println("线程信息: id = " + Thread.currentThread().getId() + " 名字 = " + Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            // 循环读取客户端发送的数据
            while (true) {
                System.out.println("线程信息: id = " + Thread.currentThread().getId() + " 名字 = " + Thread.currentThread().getName());
                System.out.println("reading...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                } else {
                    System.out.println("读取结束");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }
    }
}
