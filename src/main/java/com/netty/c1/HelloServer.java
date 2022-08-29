package com.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
        // 1. 启动器 负责组装netty组件 启动服务器
        new ServerBootstrap()
                // 2. BossEventLoop , WorkerEventLoop(selector,thread) 组 对selector进行了封装 用于处理accept read write事件
                // 16. 由某个EventLoop处理 read 事件 接收到ByteBuf
                .group(new NioEventLoopGroup())
                // 3. 对原生ServerSocketChannel 进行封装
                .channel(NioServerSocketChannel.class)
                // 4. Boss 负责处理连接 worker(child) 负责处理读写 , 决定能执行哪些操作 (handler)
                .childHandler(
                        // 5. channel 代表和客户端进行数据读写的通道,负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            // 12. 在建立连接后被调用(初始化)
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                // 17. 调用处理器
                                ch.pipeline().addLast(new StringDecoder());// 将ByteBuf 转化为字符串
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() { // 自定义 handler
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println(msg);
                                    }
                                });
                            }
                        })
                // 6. 绑定监听端口
                .bind(8080);
    }
}
