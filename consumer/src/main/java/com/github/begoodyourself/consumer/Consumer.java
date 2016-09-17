package com.github.begoodyourself.consumer;

import com.github.begoodyourself.api.bo.Container;
import com.github.begoodyourself.api.codec.ProtobufRequestDecoder;
import com.github.begoodyourself.api.codec.ProtobufResponseEncoder;
import com.github.begoodyourself.api.util.NetUtils;
import com.github.begoodyourself.api.util.StringUtils;
import com.github.begoodyourself.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;


/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */


public class Consumer implements Container{
    private EventLoopGroup boss;

    private EventLoopGroup worker;

    private ConsumerContext consumerContext;

    private ServiceRegistry serviceRegistry;

    private String serverAddress;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setConsumerContext(ConsumerContext consumerContext) {
        this.consumerContext = consumerContext;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    @Override
    public void start() {
        if(boss == null){
            boss = new NioEventLoopGroup(  );
        }
        if(worker == null){
            worker = new NioEventLoopGroup( Runtime.getRuntime().availableProcessors() * 2 );
        }
        String[] ses = StringUtils.split(serverAddress,':');
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            ChannelFuture sync = serverBootstrap.group( boss, worker ).channel( NioServerSocketChannel.class ).childHandler( new ChannelInitializer<SocketChannel>( ) {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline( ).addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ),
                            new ProtobufRequestDecoder( ), new ProtobufResponseEncoder( ), new ConsumerRpcHandler( consumerContext ) );
                }
            } ).bind( Integer.parseInt(ses[1]) ).sync( );

            serviceRegistry.register(serverAddress);
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
    }

    @Override
    public void stop() {
        if(worker != null){
            worker.shutdownGracefully();
        }
        if(boss != null){
            boss.shutdownGracefully();
        }

    }
}
