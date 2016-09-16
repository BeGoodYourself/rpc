package com.github.begoodyourself.api.bo;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public abstract class RpcMessage<T> {

    protected String messageId;
    protected GeneratedMessageV3 body;

    public abstract ByteBuf encode();
    public abstract T decode(ByteBuf src);

    protected byte[] toByteArray(ByteBuf buf){
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes, 0, bytes.length);
        return bytes;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public GeneratedMessageV3 getBody() {
        return body;
    }

    public void setBody(GeneratedMessageV3 body) {
        this.body = body;
    }
}
