package com.ognice.loadbaland;

import com.ognice.client.LocalServicesManager;
import com.ognice.module.DiscoveryService;

import java.util.List;
import java.util.Random;

/**
 * <p>Title: RandomLoadbalanceClient</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
public class RandomLoadbalanceClient implements LoadbalanceClient {
    @Override
    public String loadbalance(String serviceName) {

        List<String> remoteInstances = LocalServicesManager.localServices.get(serviceName);
        if (remoteInstances == null || remoteInstances.isEmpty()) {
            throw new RuntimeException("can not find service " + serviceName);
        } else {
            int randomIndex = new Random().nextInt(remoteInstances.size());
            return remoteInstances.get(randomIndex);
        }
    }
}
