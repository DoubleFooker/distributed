package com.ognice.register;

import com.ognice.client.AbstractDiscoveryClient;
import com.ognice.client.LocalServicesManager;
import com.ognice.config.ConfigInstance;
import com.ognice.config.RegisterConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DiscoveryClient implements AbstractDiscoveryClient {


    @Override
    public void init() {
        String regPath = ConfigInstance.REG_PATH;
        try {
            initData(regPath);
        } catch (Exception e) {
            log.warn("init err!");
        }
    }

    private void initData(String regPath) throws Exception {
        RegisterConfig.getInstance().getClient().getData().usingWatcher(
                (Watcher) watchedEvent -> {
                    try {
                        initData(regPath);
                    } catch (Exception e) {
                        log.warn("init err!");
                    }
                }
        ).forPath(regPath);
        List<String> services = RegisterConfig.getInstance().getClient().getChildren().forPath(regPath);
        for (String service : services) {
            List<String> instances = RegisterConfig.getInstance().getClient().getChildren().forPath(regPath + "/" + service);
            List<String> serviceList = new ArrayList<>(instances);
            LocalServicesManager.localServices.put(service, serviceList);
        }
    }

    @Override
    public boolean register() {
        if (RegisterConfig.getInstance().isRegisterItSelf()) {

            String basePath = ConfigInstance.INSTANCE.getRegPath();
            basePath = basePath + "/" + ConfigInstance.INSTANCE.getHost() + ":" + RegisterConfig.getInstance().getPort();
            boolean exits = false;
            int retry = 1;
            do {
                try {
                    String forPath = RegisterConfig.getInstance().getClient().create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(basePath, null);
                    return true;
                } catch (KeeperException.NodeExistsException e) {
                    log.warn("node exit retry later!");
                    exits = true;
                    try {
                        TimeUnit.MILLISECONDS.sleep(RegisterConfig.getInstance().getSessionTimeOut());
                    } catch (InterruptedException ex) {
                        //ignore
                    }
                } catch (Exception e) {
                    log.warn("create path err!");
                }
                retry++;
            } while (exits && retry < 3);
        }
        return false;
    }

    @Override
    public boolean disRegister() {
        String basePath = ConfigInstance.INSTANCE.getRegPath();
        basePath = basePath + "/" + ConfigInstance.INSTANCE.getHost() + ":" + RegisterConfig.getInstance().getPort();
        try {
            RegisterConfig.getInstance().getClient().delete().forPath(basePath);
        } catch (Exception e) {
            log.warn("del path err!", e);
        }
        return false;
    }

    @Override
    public void doHeartBeat() {
        // ignore
    }

}
