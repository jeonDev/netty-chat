package com.chat.main.socket.handler;

import com.chat.main.application.chat.domain.MessageType;
import com.chat.main.socket.telegram.Chatting;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class SocketServerHandler implements ChannelInboundHandler {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.add(channel);
        log.info("[Handler] {} has joined! Total clients: {}", channel.remoteAddress(), channels.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.remove(channel);

        log.info("[Handler] {} has left! Total clients: {}", channel.remoteAddress(), channels.size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String message = (String) msg;
        log.info("[Handler] Message : {}", msg);

        Channel channel = ctx.channel();

        Chatting telegram = Chatting.builder()
                .messageType(MessageType.MESSAGE)
                .message(message)
                .build();

        channels.forEach(item -> {
            if (item != channel) {
                item.writeAndFlush(telegram);
            }
        });
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
         log.info("[Handler] channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
         log.info("[Handler] channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("[Handler] channelActive: {}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("[Handler] channelInactive: {}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        log.info("[Handler] userEventTriggered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        log.info("[Handler] channelWritabilityChanged");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("[Handler] exceptionCaught", cause);
        ctx.close();
    }
}
