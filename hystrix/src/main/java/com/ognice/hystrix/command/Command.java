package com.ognice.hystrix.command;

public interface Command<T> {
    T run();

    T fallBack();
}
