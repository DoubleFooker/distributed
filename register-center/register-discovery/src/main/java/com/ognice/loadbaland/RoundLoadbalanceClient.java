package com.ognice.loadbaland;

import com.ognice.module.ServiceManager;

import java.util.List;

/**
 * <p>Title: RandomLoadbalanceClient</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
public class RoundLoadbalanceClient implements LoadbalanceClient {
    private Integer current = 0;

    @Override
    public String loadbalance(String serviceName) {

        List<String> instances = ServiceManager.services.get(serviceName);
        if (instances == null || instances.isEmpty()) {
            throw new RuntimeException("can not find service " + serviceName);
        } else {
            if (current > instances.size() - 1) {
                current = 0;
            }
            return instances.get(current++);
        }
    }
}
