package com.chat.main.socket.handler;

import com.chat.main.application.chat.domain.MessageType;
import com.chat.main.socket.telegram.Telegram;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ReadHandler readHandler;
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public WebSocketServerHandler(ReadHandler readHandler) {
        this.readHandler = readHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.add(channel);
        log.info("[Handler] {} has joined! Total clients: {}", channel.remoteAddress(), channels.size());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.remove(channel);

        log.info("[Handler] {} has left! Total clients: {}", channel.remoteAddress(), channels.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        log.info("[Handler] Message : {}", msg);

        Telegram telegram = readHandler.readToMakeTelegram(MessageType.MESSAGE, msg.text());

        Channel channel = ctx.channel();

        channels.forEach(item -> {
            if (item != channel) {
                item.writeAndFlush(telegram);
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[Handler] exceptionCaught", cause);
        ctx.close();
    }
}
