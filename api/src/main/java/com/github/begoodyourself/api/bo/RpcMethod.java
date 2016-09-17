package com.github.begoodyourself.api.bo;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RpcMethod {
    Class<? extends GeneratedMessageV3> args();
}
