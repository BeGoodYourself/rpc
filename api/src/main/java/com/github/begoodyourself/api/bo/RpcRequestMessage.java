package com.github.begoodyourself.api.bo;

import com.github.begoodyourself.api.protos.ProtobufRpcMessage.*;
import com.github.begoodyourself.api.util.ProtoUtils;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class RpcRequestMessage extends RpcMessage<RpcRequestMessage>{
    protected String requestService;
    protected String requestMethod;

    @Override
    public ByteBuf encode() {
        byte[] bytes = RpcRequestProto.newBuilder()
                .setMessageId(messageId)
                .setRequestService(requestService)
                .setRequestMethod(requestMethod)
                .setRequestParameterProto(body.getClass().getName())
                .setRequestBody(body.toByteString()).build().toByteArray();
         ByteBuf buf = Unpooled.buffer(bytes.length + 4);
         buf.writeInt(bytes.length);
         buf.writeBytes(bytes);
        return buf;
    }

    @Override
    public RpcRequestMessage decode(ByteBuf src) {
        try {
            RpcRequestProto request =  RpcRequestProto.newBuilder().mergeFrom(toByteArray(src)).build();
            messageId = request.getMessageId();
            requestService = request.getRequestService();
            requestMethod = request.getRequestMethod();
            body = (GeneratedMessageV3) ProtoUtils.find(request.getRequestParameterProto()).toBuilder().mergeFrom(request.getRequestBody()).build();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpcRuntimeException(e,ErrorCode.SYSTEM_ERROR);
        }
    }

    public String getRequestService() {
        return requestService;
    }

    public void setRequestService(String requestService) {
        this.requestService = requestService;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getKey(){
        return requestService+"_"+requestMethod+"_"+ body.getDescriptorForType().getFields().size();
    }
}
