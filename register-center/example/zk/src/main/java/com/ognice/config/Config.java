package com.ognice.config;

import com.ognice.loadbaland.LoadbalanceClient;
import com.ognice.loadbaland.RandomLoadbalanceClient;
import com.ognice.register.DiscoveryClient;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * <p>Title: Config</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@Data
@Accessors(chain = true)
@Configuration
public class Config {
    @Value("${zkAddress:127.0.0.1:2181}")
    private String zkAddress;

    @Bean
    public DiscoveryClient discoveryClient() {
        DiscoveryClient discoveryClient = new DiscoveryClient();
        discoveryClient.init();
        new Thread(()->{
            discoveryClient.register();
        },"register-thread").start();
        return discoveryClient;
    }

    @Bean
    public LoadbalanceClient loadbalanceClient() {
        return new RandomLoadbalanceClient();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    Environment environment;

    @PostConstruct
    public void init() {
        RegisterConfig.getInstance().setPort(environment.getProperty("server.port"));
    }
}
