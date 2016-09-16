package com.github.begoodyourself.producer;

import com.github.begoodyourself.api.bo.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProducerHandler extends SimpleChannelInboundHandler<RpcResponseMessage>{
    private RpcEventListener rpcEventListener;

    public ProducerHandler registerRpcEventListener(RpcEventListener rpcEventListener) {
        this.rpcEventListener = rpcEventListener;
        return this;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        if(rpcEventListener != null){
            rpcEventListener.onRead(ctx,msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       if(rpcEventListener != null){
           rpcEventListener.onConnected(ctx);
       }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
    }
}
