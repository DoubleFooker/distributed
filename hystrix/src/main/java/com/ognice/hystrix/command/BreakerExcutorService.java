package com.ognice.hystrix.command;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BreakerExcutorService {
    private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(1));

    public static ThreadPoolExecutor getExcutor() {
        return threadPoolExecutor;
    }
}
