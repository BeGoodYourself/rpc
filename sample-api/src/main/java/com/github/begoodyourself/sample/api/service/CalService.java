package com.github.begoodyourself.sample.api.service;
import com.github.begoodyourself.api.bo.RpcMethod;
import com.github.begoodyourself.api.bo.RpcService;
import com.github.begoodyourself.sample.api.protos.CalServiceProto.*;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
@RpcService
public interface CalService {

    @RpcMethod( args = FibonacciReqProto.class)
    FibonacciRespProto Fibonacci(int n);

    @RpcMethod( args = FibonacciReqProto.class)
    FibonacciRespProto Fibonacci2(int n);

    @RpcMethod( args = FibonacciReqProto.class)
    FibonacciRespProto Fibonacci3(int n);

}
