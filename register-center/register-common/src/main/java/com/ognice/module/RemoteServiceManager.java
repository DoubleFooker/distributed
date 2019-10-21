package com.ognice.module;

import java.util.*;

/**
 * <p>Title: ServiceManager</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
public class RemoteServiceManager {
    public static Map<String, Set<DiscoveryService>> services = Collections.synchronizedMap(new HashMap<>());

    public static DiscoveryService getInstance(String serviceName, String host, String port) {
        Set<DiscoveryService> discoveryServices = services.get(serviceName);
        if (discoveryServices == null) {
            return null;
        }
        DiscoveryService updated = null;
        for (DiscoveryService instance : discoveryServices) {
            if (instance.getName().equals(serviceName) && instance.getHost().equals(host) && instance.getPort().equals(port)) {
                updated = instance;
                break;
            }

        }
        return updated;
    }
}
