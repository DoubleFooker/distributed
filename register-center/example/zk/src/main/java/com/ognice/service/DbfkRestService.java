package com.ognice.service;

import com.ognice.loadbaland.LoadbalanceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>Title: DbfkRestService</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@Service
public class DbfkRestService {
    private static String serviceName = "dbfk-service";
    @Autowired
    LoadbalanceClient loadbalanceClient;
    @Autowired
    RestTemplate restTemplate;

    public String reqDemo() {
        String url = loadbalanceClient.loadbalance(serviceName);
        String object = restTemplate.getForObject("http://" + url + "/zk/service/demo", String.class);
        return object;
    }

}
