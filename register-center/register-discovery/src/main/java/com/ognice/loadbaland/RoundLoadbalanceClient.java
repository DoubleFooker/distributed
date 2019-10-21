package com.ognice.loadbaland;

import com.ognice.client.LocalServicesManager;

import java.util.List;

/**
 * <p>Title: RandomLoadbalanceClient</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
public class RoundLoadbalanceClient implements LoadbalanceClient {
    private Integer currentPickedIndex = 0;

    @Override
    public String loadbalance(String serviceName) {

        List<String> instances = LocalServicesManager.localServices.get(serviceName);
        if (instances == null || instances.isEmpty()) {
            throw new RuntimeException("can not find service " + serviceName);
        } else {
            if (currentPickedIndex > instances.size() - 1) {
                currentPickedIndex = 0;
            }
            return instances.get(currentPickedIndex++);

        }
    }
}
