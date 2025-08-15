package com.chat.main.socket.config;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketServerConfig {

    private final ServerBootstrap bootstrap;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;
    private final ChannelInitializer<SocketChannel> socketServerInitializer;

    public SocketServerConfig(ChannelInitializer<SocketChannel> socketServerInitializer) {
        this.bootstrap   = new ServerBootstrap();
        this.bossGroup   = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup();
        this.socketServerInitializer = socketServerInitializer;
    }


    @Bean
    public ServerBootstrap serverBootStrap() {
        return bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10)
                .option(ChannelOption.SO_BACKLOG, 50)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(socketServerInitializer);
    }

    @PreDestroy
    public void destroy() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
