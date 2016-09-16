package com.github.begoodyourself.consumer;

import com.github.begoodyourself.api.bo.ErrorCode;
import com.github.begoodyourself.api.bo.RpcRequestMessage;
import com.github.begoodyourself.api.bo.RpcResponseMessage;
import com.github.begoodyourself.api.bo.RpcRuntimeException;
import com.github.begoodyourself.consumer.bo.MethodInvoker;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ConsumerRpcHandler extends SimpleChannelInboundHandler<RpcRequestMessage>{
    private ConsumerContext consumerContext;

    public ConsumerRpcHandler(ConsumerContext consumerContext) {
        this.consumerContext = consumerContext;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx,final RpcRequestMessage msg) throws Exception {
        consumerContext.executor( () -> {
            RpcResponseMessage rpcResponseMessage = new RpcResponseMessage();
            rpcResponseMessage.setMessageId( msg.getMessageId() );
            try{
                MethodInvoker methodInvoker = consumerContext.find( msg.getKey( ) );
                GeneratedMessageV3 body = msg.getBody();
                List<Descriptors.FieldDescriptor> fieldDescriptorList = body.getDescriptorForType().getFields();
                Object[] params = new Object[fieldDescriptorList.size()];
                for (int i = 0; i < params.length; i++) {
                    params[i] = body.getField(fieldDescriptorList.get(i));
                }
                rpcResponseMessage.setBody( ( GeneratedMessageV3 ) methodInvoker.invoke( params ) );
            }catch (Exception e){
                if(e instanceof RpcRuntimeException){
                    rpcResponseMessage.setErrorcode( ((RpcRuntimeException)e).getErrorcode() );
                    rpcResponseMessage.setErrMessage( ((RpcRuntimeException)e).getErrorMsg() );
                }else{
                    rpcResponseMessage.setErrorcode( ErrorCode.SYSTEM_ERROR );
                    rpcResponseMessage.setErrMessage( e.getMessage() );
                }
            }
            ctx.writeAndFlush( rpcResponseMessage );
        } );
    }
}
