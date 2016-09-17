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
public class RpcResponseMessage extends RpcMessage<RpcResponseMessage>{

    protected int errorcode = ErrorCode.SUCCESS;
    protected String errMessage = "";

    @Override
    public ByteBuf encode() {
        byte[] bytes = RpcResponseProto.newBuilder()
                .setMessageId(messageId)
                .setErrorcode(errorcode)
                .setErrormsg(errMessage)
                .setResponseProto(body.getClass().getName())
                .setResponseBody(body.toByteString()).build().toByteArray();
        ByteBuf buf = Unpooled.buffer(bytes.length + 4);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
        return buf;
    }

    @Override
    public RpcResponseMessage decode(ByteBuf src) {
        try {
            RpcResponseProto responseProto = RpcResponseProto.newBuilder().mergeFrom(toByteArray(src)).build();
            messageId = responseProto.getMessageId();
            errorcode = responseProto.getErrorcode();
            errMessage = responseProto.getErrormsg();
            body = (GeneratedMessageV3) ProtoUtils.find(responseProto.getResponseProto()).toBuilder().mergeFrom(responseProto.getResponseBody()).build();
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpcRuntimeException(e, ErrorCode.SYSTEM_ERROR);
        }
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
