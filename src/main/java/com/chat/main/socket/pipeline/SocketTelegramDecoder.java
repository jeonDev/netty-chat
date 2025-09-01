package com.chat.main.socket.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class SocketTelegramDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        log.info("[Socket] Decoder : {}" , in.readableBytes());

        ByteBuf buffer = in.readBytes(in.readableBytes());
        String message = buffer.toString(UTF_8);
        out.add(message);
    }
}
