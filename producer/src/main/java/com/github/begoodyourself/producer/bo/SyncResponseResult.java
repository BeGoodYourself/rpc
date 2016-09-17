package com.github.begoodyourself.producer.bo;

import com.github.begoodyourself.api.bo.RpcResponseMessage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with rpc
 * AUTHOR ; BEGOODYOURSELF
 * DATE : 2016/9/16
 */
public class SyncResponseResult {
    private Lock lock = new ReentrantLock();
    private Condition sig = lock.newCondition();
    private RpcResponseMessage rpcResponseMessage;
    public Object getResult()throws Exception{
        try {
            lock.lock();
            sig.await(100, TimeUnit.SECONDS);
            if(rpcResponseMessage != null){
                return rpcResponseMessage.getBody();
            }
            return null;
        }finally {
            lock.unlock();
        }
    }

    public void readResponse(RpcResponseMessage rpcResponseMessage){
        try{
            lock.lock();
            this.rpcResponseMessage = rpcResponseMessage;
            sig.signal();
        }finally {
            lock.unlock();
        }
    }
}
