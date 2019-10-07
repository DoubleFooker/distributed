package com.ognice.config;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ZkConfig {
    private String zkAddress;

    private ZkConfig() {
    }

    private static final ZkConfig instance = new ZkConfig();

    public static ZkConfig getInstance() {
        return instance;
    }

}
