package com.github.begoodyourself.producer.proxy;

import com.github.begoodyourself.api.bo.RpcMethod;
import com.github.begoodyourself.api.bo.RpcRequestMessage;
import com.github.begoodyourself.api.util.ProtoUtils;
import com.github.begoodyourself.producer.Producer;
import com.google.protobuf.Descriptors.*;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.ProtocolMessageEnum;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.UUID;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class JdkProxy {

    public static Object proxy(final Producer producer, Class interfaceCls)throws  Exception{
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{interfaceCls}, (proxy, method, args) -> {
            RpcMethod rpcMethod = method.getAnnotation(RpcMethod.class);
            GeneratedMessageV3 instance = ProtoUtils.find(rpcMethod.args().getName());
            GeneratedMessageV3.Builder builder = (GeneratedMessageV3.Builder) instance.newBuilderForType();
            List<FieldDescriptor> fieldDescriptorList = instance.getDescriptorForType().getFields();
            for (int i = 0; i < fieldDescriptorList.size(); i++) {
                Object value = args[i];
                if(value instanceof ProtocolMessageEnum){
                    value = ((ProtocolMessageEnum)value).getValueDescriptor();
                }
                builder.setField(fieldDescriptorList.get(i),value);
            }

            RpcRequestMessage requestWrapper = new RpcRequestMessage();
            requestWrapper.setMessageId(UUID.randomUUID().toString());
            requestWrapper.setBody((GeneratedMessageV3) builder.build());
            requestWrapper.setRequestMethod(method.getName());
            requestWrapper.setRequestService(method.getDeclaringClass().getName());
            return producer.write(requestWrapper);
        });
    }
}
