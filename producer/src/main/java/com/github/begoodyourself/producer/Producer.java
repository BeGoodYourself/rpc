package com.github.begoodyourself.producer;

import com.github.begoodyourself.api.bo.Container;
import com.github.begoodyourself.api.bo.RpcRequestMessage;
import com.github.begoodyourself.api.bo.RpcResponseMessage;
import com.github.begoodyourself.api.codec.ProtobufRequestEncoder;
import com.github.begoodyourself.api.codec.ProtobufResponseDecoder;
import com.github.begoodyourself.api.util.StringUtils;
import com.github.begoodyourself.producer.bo.SyncResponseResult;
import com.github.begoodyourself.registry.ServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class Producer implements RpcEventListener,Container {
    protected EventLoopGroup boss ;
    protected ChannelHandlerContext channelHandlerContext;
    protected ConcurrentHashMap<String, SyncResponseResult> syncResponseResultCaches = new ConcurrentHashMap<>();
    private ServiceDiscovery serviceDiscovery;

    public void setServiceDiscovery(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @Override
    public void start(){
        if(boss == null){
            boss = new NioEventLoopGroup();
        }
        String[] servers = serviceDiscovery.discover().split(",");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(boss).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4,0,4)
                ,new ProtobufRequestEncoder(), new ProtobufResponseDecoder(), new ProducerHandler().registerRpcEventListener(Producer.this));
            }
        }).connect(servers[0], Integer.parseInt(servers[1]));
    }

    @Override
    public void stop(){
        boss.shutdownGracefully();
        syncResponseResultCaches.clear();
    }

    @Override
    public void onConnected(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void onRead(ChannelHandlerContext channelHandlerContext, RpcResponseMessage message) {
        SyncResponseResult responseResult = syncResponseResultCaches.remove(message.getMessageId());
        if(responseResult != null){
            responseResult.readResponse(message);
        }
    }

    public Object write(RpcRequestMessage rpcRequestMessage) throws Exception{
        SyncResponseResult syncResponseResult = new SyncResponseResult();
        syncResponseResultCaches.put(rpcRequestMessage.getMessageId(),syncResponseResult);
        channelHandlerContext.writeAndFlush(rpcRequestMessage);
        return syncResponseResult.getResult();
    }
}
