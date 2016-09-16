package com.github.begoodyourself.consumer;

import com.github.begoodyourself.api.bo.Container;
import com.github.begoodyourself.api.codec.ProtobufRequestDecoder;
import com.github.begoodyourself.api.codec.ProtobufResponseEncoder;
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

    public Consumer(ConsumerContext consumerContext) {
        this.consumerContext = consumerContext;
    }

    @Override
    public void start() {
        if(boss == null){
            boss = new NioEventLoopGroup(  );
        }
        if(worker == null){
            worker = new NioEventLoopGroup( Runtime.getRuntime().availableProcessors() * 2 );
        }

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            ChannelFuture sync = serverBootstrap.group( boss, worker ).channel( NioServerSocketChannel.class ).childHandler( new ChannelInitializer<SocketChannel>( ) {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline( ).addLast( new LengthFieldBasedFrameDecoder( Integer.MAX_VALUE, 0, 4, 0, 4 ),
                            new ProtobufRequestDecoder( ), new ProtobufResponseEncoder( ), new ConsumerRpcHandler( consumerContext ) );
                }
            } ).bind( 8899 ).sync( );
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
