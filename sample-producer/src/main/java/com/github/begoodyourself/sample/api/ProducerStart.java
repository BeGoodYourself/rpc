package com.github.begoodyourself.sample.api;

import com.github.begoodyourself.producer.Producer;
import com.github.begoodyourself.producer.proxy.CglibProxy;
import com.github.begoodyourself.sample.api.service.CalService;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProducerStart {
    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.start();
        try {
            CalService calService = ( CalService )CglibProxy.proxy( producer, CalService.class );
            System.out.println( calService.Fibonacci( 10 ));
            System.out.println( calService.Fibonacci2( 10 ));
            System.out.println( calService.Fibonacci3( 10 ));
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }
}
