package com.github.begoodyourself.api.bo;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
@Retention(RetentionPolicy.RUNTIME )
@Target(ElementType.TYPE )
@Component
public @interface RpcService {
}
