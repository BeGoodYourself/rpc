package com.github.begoodyourself.api.bo;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public @interface RpcMethod {
    Class<? extends GeneratedMessageV3> args();
}
