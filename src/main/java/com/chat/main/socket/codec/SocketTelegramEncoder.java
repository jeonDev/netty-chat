package com.chat.main.socket.codec;

import com.chat.main.socket.telegram.Telegram;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SocketTelegramEncoder extends MessageToByteEncoder<Telegram> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Telegram msg, ByteBuf out) throws Exception {

    }
}
