package com.github.begoodyourself.sample.api;

import com.github.begoodyourself.api.bo.RpcMethod;
import com.github.begoodyourself.api.bo.RpcService;
import com.github.begoodyourself.sample.api.protos.CalServiceProto.*;
import com.github.begoodyourself.sample.api.service.CalService;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
@RpcService
public class CalServiceImpl implements CalService{
    @RpcMethod( args = FibonacciReqProto.class)
    @Override
    public FibonacciRespProto Fibonacci(int n) {
        return FibonacciRespProto.newBuilder().setResult( f1( n ) ).build();
    }
    @RpcMethod( args = FibonacciReqProto.class)
    @Override
    public FibonacciRespProto Fibonacci2(int n) {
        return FibonacciRespProto.newBuilder().setResult( f2( n ) ).build();
    }
    @RpcMethod( args = FibonacciReqProto.class)
    @Override
    public FibonacciRespProto Fibonacci3(int n) {
        return FibonacciRespProto.newBuilder().setResult( f3( n ) ).build();
    }

    static long f1(int n){
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else if (n > 1)
            return f1(n - 1) + f1(n - 2);
        else
            return -1;
    }
    public static long f2(int n){ //循环函数算法
         int a=1,b=1,c=0;
        for(int i=2;i < n; c=a+b)
        {
            a=b;
            b=c;
        }
        return c;
    }

    static long f3(int n){
        return (long) ((Math.pow((1+Math.sqrt(5.0))/2,n)-Math.pow((1-Math.sqrt(5.0))/2,n))/Math.sqrt(5.0));
    }
}
