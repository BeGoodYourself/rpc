package com.github.begoodyourself.registry;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with rpc
 *
 * @author ; BEGOODYOURSELF
 *         DATE : 2016/9/17
 */
public class ServiceDiscovery extends ZooKeeperRegistry{

    private volatile List<String> dataList = new ArrayList<>();

    private void watchNode(final ZooKeeper zk){
       try {
           List<String> nodeList =  zk.getChildren("Root", new Watcher() {
               @Override
               public void process(WatchedEvent watchedEvent) {
                   if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                       watchNode(zk);
                   }
               }
           });

            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData("Root" + node, false, null);
                dataList.add(new String(bytes));
            }
            this.dataList = dataList;
            if (dataList.isEmpty()) {
                throw new RuntimeException("尚未注册任何服务");
            }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public String discover(){
        if(dataList.size() > 1){
            return dataList.get(ThreadLocalRandom.current().nextInt(dataList.size()));
        }
        if(dataList.size() == 1){
            return dataList.get(0);
        }
        throw new RuntimeException("");
    }

    public void init(){
        ZooKeeper zk = connectServer();
        watchNode(zk);
    }
}
