package com.ognice.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data()
@Accessors(chain = true)
public class RegisterConfig {
    private String serviceName = UUID.randomUUID().toString();
    private boolean registerItSelf = true;
    private RegisterConfig instance = new RegisterConfig();

    private RegisterConfig() {
    }

    public RegisterConfig getInstance() {
        return instance;
    }

}
