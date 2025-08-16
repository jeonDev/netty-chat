package com.chat.main.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketServerStarter {

    private final int port;
    private final ServerBootstrap serverBootStrap;

    public SocketServerStarter(@Value("${socket.chat.server.port}") int port,
                               ServerBootstrap serverBootStrap) throws InterruptedException {
        this.port = port;
        this.serverBootStrap = serverBootStrap;
        this.start();
    }

    public void start() throws InterruptedException {
        log.info("[Netty] Server Start");
        ChannelFuture future = serverBootStrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }

}
