package com.ognice.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

@Data
@Accessors(chain = true)
public class RegisterConfig {
    private String serviceName = "dbfk-service";
    private String port;
    private boolean registerItSelf = true;
    private int sessionTimeOut = 10000;
    private static RegisterConfig instance = new RegisterConfig();
    private CuratorFramework client = CuratorFrameworkFactory.newClient(ZkConfig.getInstance().getZkAddress(),
            sessionTimeOut, 10000, new RetryOneTime(10000));

    {
        client.start();
    }

    private RegisterConfig() {
    }

    public static RegisterConfig getInstance() {
        return instance;
    }

}
