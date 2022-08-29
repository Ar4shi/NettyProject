package com.netty.c2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class EventLoopServer {
    public static void main(String[] args) {
        // 细分2 ：创建一个独立的EventLoopGroup,可以用来用于处理普通耗时事件 和 worker分开来，worker只用来处理io事件
        DefaultEventLoopGroup group = new DefaultEventLoopGroup();
        new ServerBootstrap()
                // 细分1 : boss(第一个参数 只负责accept) 和 worker(第二个参数 负责read write)划分更细
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast("handler1", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(Thread.currentThread().getName() + " | " + buf.toString(Charset.defaultCharset()));
                                        // 传递给下一个handler
                                        ctx.fireChannelRead(msg);
                                    }
                                })
                                .addLast(group, "handler2", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(Thread.currentThread().getName() + " | " + buf.toString(Charset.defaultCharset()));
                                    }
                                });
                    }
                }).bind(8080);
    }
}
