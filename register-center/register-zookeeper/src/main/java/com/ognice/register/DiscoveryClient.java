package com.ognice.register;

import com.ognice.client.AbstractDiscoveryClient;
import com.ognice.config.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import sun.net.util.IPAddressUtil;

import java.net.InetAddress;

public class DiscoveryClient implements AbstractDiscoveryClient {
    @Override
    public boolean register(String serviceName) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(ZkConfig.getInstance().getZkAddress(), new RetryOneTime(10000));
        curatorFramework.start();

        return false;
    }

    @Override
    public boolean disRegister(String serviceName) {
        return false;
    }

    @Override
    public boolean doHeartBeat(String serviceName) {
        return false;
    }
}
