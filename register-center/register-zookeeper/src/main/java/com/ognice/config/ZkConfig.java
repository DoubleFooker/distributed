package com.ognice.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ZkConfig {
    private String zkAddress="127.0.0.1:2181";

    private ZkConfig() {
    }

    private static final ZkConfig instance = new ZkConfig();

    public static ZkConfig getInstance() {
        return instance;
    }

}
