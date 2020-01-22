package com.ognice.hystrix.command;

import java.util.Random;

public class RequestBreakerCommand implements Command<String> {

    @Override
    public String run() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(150));
        } catch (InterruptedException e) {
            //
        }
        return "正常";
    }

    @Override
    public String fallBack() {
        return "降级";
    }
}