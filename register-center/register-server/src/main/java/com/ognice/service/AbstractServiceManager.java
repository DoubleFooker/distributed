package com.ognice.service;


import com.ognice.module.DiscoveryService;

import java.util.List;
import java.util.Map;

public interface AbstractServiceManager {

    Map<String,List<DiscoveryService>> getAllServices();
    List<DiscoveryService> getServicesByName(String serviceName);
}
