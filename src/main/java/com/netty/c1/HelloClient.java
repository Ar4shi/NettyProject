package com.netty.c1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        // 7. 客户端启动类
        new Bootstrap()
                // 8. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 9. 选择客户端channel 实现
                .channel(NioSocketChannel.class)
                // 10. 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    // 12. 在建立连接后被调用(初始化)
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 15. 调用处理器
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 11 连接到服务器
                .connect(new InetSocketAddress("localhost",8080))
                // 13 阻塞方法，知道连接建立
                .sync()
                // 代表连接对象
                .channel()
                // 14. 向服务器发送数据
                .writeAndFlush("hello,world");
    }
}
