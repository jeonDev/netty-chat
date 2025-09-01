package com.chat.main.socket.pipeline;

import com.chat.main.socket.telegram.Telegram;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class SocketTelegramEncoder extends MessageToByteEncoder<Telegram> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Telegram telegram, ByteBuf out) {
        log.info("[Socket] Encoder");

        String message = telegram.parse();
        byte[] bytes = message.getBytes(UTF_8);

        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
