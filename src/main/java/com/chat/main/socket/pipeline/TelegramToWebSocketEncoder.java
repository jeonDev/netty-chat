package com.chat.main.socket.pipeline;

import com.chat.main.socket.telegram.Telegram;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

public class TelegramToWebSocketEncoder extends MessageToMessageEncoder<Telegram> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Telegram msg, List<Object> out) {
        out.add(new TextWebSocketFrame(msg.parse()));
    }
}
