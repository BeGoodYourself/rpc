package com.github.begoodyourself.consumer;

import com.github.begoodyourself.api.bo.RpcMethod;
import com.github.begoodyourself.api.bo.RpcService;
import com.github.begoodyourself.consumer.bo.MethodInvoker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class ConsumerContext implements InitializingBean, ApplicationContextAware{
    private Executor executor;

    private Map<String, MethodInvoker> methodInvokers = new HashMap<>(  );
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation( RpcService.class );
        for(Object entry : beansWithAnnotation.values()){
            Class<?>[] interfaces = entry.getClass( ).getInterfaces( );
            Class rpcInterface = null;
            for ( Class c:interfaces) {
                if( c.isAnnotationPresent( RpcService.class )){
                    rpcInterface = c;
                    break;
                }
            }

            if(rpcInterface == null){
                throw new RuntimeException(entry.getClass().getName()+ " not exist interface that has RpcService.class Annotation" );
            }

            for ( Method m: entry.getClass().getDeclaredMethods() ) {
                if(!m.isAnnotationPresent( RpcMethod.class )){
                    continue;
                }
                FastClass fastCls = FastClass.create( entry.getClass() );
                FastMethod fastMethod = fastCls.getMethod( m );
                methodInvokers.put( rpcInterface.getName()+"_"+ m.getName()+"_"+ m.getParameters().length , new MethodInvoker( fastMethod, entry ) );
            }
        }

        executor = Executors.newCachedThreadPool();
    }

    public MethodInvoker find(String key){
        return methodInvokers.get( key );
    }

    public void executor(Runnable task){
        executor.execute( task );
    }
}
