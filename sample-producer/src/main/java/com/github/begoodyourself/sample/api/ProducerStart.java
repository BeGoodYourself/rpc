package com.github.begoodyourself.sample.api;

import com.github.begoodyourself.producer.Producer;
import com.github.begoodyourself.producer.proxy.JdkProxy;
import com.github.begoodyourself.sample.api.service.CalService;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProducerStart {
    public static void main(String[] args) {
        Producer producer = new Producer();
        try {
            CalService calService = ( CalService ) JdkProxy.proxy( producer, CalService.class );
            System.out.println( calService.Fibonacci( 100 ));
            System.out.println( calService.Fibonacci2( 100 ));
            System.out.println( calService.Fibonacci3( 100 ));
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }
}
