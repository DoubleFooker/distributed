package com.ognice.client;

public interface AbstractDiscoveryClient {

    void init();

    boolean register();

    boolean disRegister();

    void doHeartBeat();

}
