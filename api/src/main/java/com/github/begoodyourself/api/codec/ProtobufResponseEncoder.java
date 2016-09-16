package com.github.begoodyourself.api.codec;

import com.github.begoodyourself.api.bo.RpcResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProtobufResponseEncoder extends MessageToByteEncoder<RpcResponseMessage>{
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcResponseMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.encode());
    }
}
