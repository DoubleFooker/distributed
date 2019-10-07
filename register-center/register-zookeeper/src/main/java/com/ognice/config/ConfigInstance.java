package com.ognice.config;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
@Slf4j
public enum ConfigInstance {
    INSTANCE;
    public String getHost()  {
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("get host err!");
        }
        return hostAddress;
    }
    public static final String REG_PATH="dbfkRegCenter";
}
