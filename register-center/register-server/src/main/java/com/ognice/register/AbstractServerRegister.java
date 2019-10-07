package com.ognice.register;

public interface AbstractServerRegister {

    boolean register(String serviceName, String address);

    boolean disRegister(String serviceName, String address);

    boolean heartBeat(String serviceName, String address);
}
