package com.github.begoodyourself.sample.api;

import com.github.begoodyourself.consumer.Consumer;
import com.github.begoodyourself.consumer.ConsumerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ConsumerStarter {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext( "classpath:app.xml" );
        ConsumerContext consumerContext = ( ConsumerContext ) ac.getBean( "consumerContext" );
        Consumer consumer = new Consumer( consumerContext );
        consumer.start();

    }
}
