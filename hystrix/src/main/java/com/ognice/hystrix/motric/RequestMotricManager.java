package com.ognice.hystrix.motric;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestMotricManager {
    private static ConcurrentHashMap<String, AtomicInteger> badRequestMotric = new ConcurrentHashMap<>();

    public void incBadRequest(String serviceId) {
        int andIncrement = badRequestMotric.get(serviceId).getAndIncrement();
    }
}
