package com.chat.main.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketServerStarter {

    private final int port;
    private final ServerBootstrap serverBootStrap;

    public SocketServerStarter(@Value("${socket.chat.server.port}") int port,
                               ServerBootstrap serverBootStrap
    ) {
        this.port = port;
        this.serverBootStrap = serverBootStrap;
    }

    private void start() throws InterruptedException {
        log.info("[Netty] Server Start");
        ChannelFuture future = serverBootStrap.bind(port).sync();
        future.channel().closeFuture().sync();
    }

    @PostConstruct
    public void startNettyServer() {
        new Thread(() -> {
            try {
                this.start();
            } catch (InterruptedException e) {
                log.error("Netty Server 실행 중 오류 발생 : {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }, "Netty-Tcp").start();
    }

}
