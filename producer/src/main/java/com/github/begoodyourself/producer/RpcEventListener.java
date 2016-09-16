package com.github.begoodyourself.producer;

import com.github.begoodyourself.api.bo.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public interface RpcEventListener {
    public void onConnected(ChannelHandlerContext channelHandlerContext);

    public void onRead(ChannelHandlerContext channelHandlerContext, RpcResponseMessage message);
}
