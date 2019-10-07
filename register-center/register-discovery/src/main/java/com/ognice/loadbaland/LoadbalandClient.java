package com.ognice.loadbaland;

import com.ognice.module.DiscoveryService;

import java.rmi.server.RemoteServer;

public interface LoadbalandClient {

    DiscoveryService loadbalandDelge();
}
