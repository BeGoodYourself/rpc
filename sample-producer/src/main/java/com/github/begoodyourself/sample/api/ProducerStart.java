package com.github.begoodyourself.sample.api;

import com.github.begoodyourself.producer.Producer;
import com.github.begoodyourself.producer.proxy.CglibProxy;
import com.github.begoodyourself.sample.api.service.CalService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ProducerStart {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:app.xml");
        Producer producer = (Producer)ac.getBean("producer");
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
