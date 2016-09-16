package com.github.begoodyourself.consumer.bo;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class MethodInvoker {
    private FastMethod method;
    private Object instance;

    public MethodInvoker(FastMethod method, Object instance) {
        this.method = method;
        this.instance = instance;
    }

    public Object invoke(Object[] params) throws InvocationTargetException {
        return method.invoke( instance, params );
    }
}
