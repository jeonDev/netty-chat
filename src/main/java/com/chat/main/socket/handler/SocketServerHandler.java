package com.chat.main.socket.handler;

import com.chat.main.application.chat.domain.MessageType;
import com.chat.main.socket.telegram.Chatting;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class SocketServerHandler implements ChannelInboundHandler {

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
        log.info("[Handler] channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("[Handler] channelInactive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("[Handler] channelRead");
        String message = (String) msg;
        log.info("[Handler] Message : {}", msg);

        Chatting telegram = Chatting.builder()
                .messageType(MessageType.MESSAGE)
                .message(message)
                .build();
        ctx.writeAndFlush(telegram);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("[Handler] channelReadComplete");
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
        log.info("[Handler] exceptionCaught");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        log.info("[Handler] handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        log.info("[Handler] handlerRemoved");
    }
}
