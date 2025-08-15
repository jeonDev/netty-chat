package com.chat.main.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketServerStarter {

    private final ServerBootstrap serverBootStrap;
    private final int PORT = 10005;

    public SocketServerStarter(ServerBootstrap serverBootStrap) throws InterruptedException {
        this.serverBootStrap = serverBootStrap;
        this.start();
    }

    public void start() throws InterruptedException {
        log.info("[Netty] Server Start");
        ChannelFuture future = serverBootStrap.bind(PORT).sync();
        future.channel().closeFuture().sync();
    }

}
