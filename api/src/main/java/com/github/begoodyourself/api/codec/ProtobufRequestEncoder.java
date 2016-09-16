package com.github.begoodyourself.api.codec;

import com.github.begoodyourself.api.bo.RpcRequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProtobufRequestEncoder extends MessageToByteEncoder<RpcRequestMessage>{
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcRequestMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.encode());
    }
}
