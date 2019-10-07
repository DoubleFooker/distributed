package com.ognice.client;

public interface AbstractDiscoveryClient {

    boolean register(String serviceName);

    boolean disRegister(String serviceName);

    boolean doHeartBeat(String serviceName);

}
