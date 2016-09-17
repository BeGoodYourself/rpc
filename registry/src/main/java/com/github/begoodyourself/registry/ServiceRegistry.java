package com.github.begoodyourself.registry;

import com.github.begoodyourself.api.util.Constants;
import org.apache.zookeeper.*;
/**
 * Created with rpc
 *
 * @author ; BEGOODYOURSELF
 * DATE : 2016/9/17
 */
public class ServiceRegistry extends ZooKeeperRegistry{
    protected String registryPath = Constants.deafult_registryPath;
    public void register(String data){
        ZooKeeper zk = connectServer();
        createNode(zk, data);
    }

    private void createNode(ZooKeeper zk, String data){
        try {
            zk.create(registryPath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
