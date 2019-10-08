package com.ognice.loadbaland;

import com.ognice.module.ServiceManager;

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

        List<String> instances = ServiceManager.services.get(serviceName);
        if (instances == null || instances.isEmpty()) {
            throw new RuntimeException("can not find service " + serviceName);
        } else {
            return instances.get(new Random().nextInt(instances.size()));
        }
    }
}
