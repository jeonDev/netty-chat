package com.chat.main.socket.handler;

import com.chat.main.socket.pipeline.SocketTelegramDecoder;
import com.chat.main.socket.pipeline.SocketTelegramEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

@Component
public class SocketServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SocketServerHandler socketServerHandler;

    public SocketServerInitializer(SocketServerHandler socketServerHandler) {
        this.socketServerHandler = socketServerHandler;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("decoder", new SocketTelegramDecoder());
        pipeline.addLast("encoder", new SocketTelegramEncoder());
        pipeline.addLast("handler", socketServerHandler);
    }
}
