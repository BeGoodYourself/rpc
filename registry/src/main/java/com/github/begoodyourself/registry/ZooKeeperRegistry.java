package com.github.begoodyourself.registry;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created with rpc
 *
 * @author ; BEGOODYOURSELF
 *         DATE : 2016/9/17
 */
public class ZooKeeperRegistry {

    protected String registryAddress;
    protected int timeout  =1000;

    protected CountDownLatch latch = new CountDownLatch(1);


    protected ZooKeeper connectServer(){
        ZooKeeper zk;
        try {
            zk = new ZooKeeper(registryAddress, timeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                        latch.countDown();
                    }
                }
            });
            latch.await();
            return zk;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }
}
