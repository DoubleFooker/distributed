package com.ognice.services;

import com.ognice.module.DiscoveryService;
import com.ognice.module.RemoteServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: RegisterService</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@Service
public class RegisterService {
    public DiscoveryService reg(String name, String host, String port) {
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DiscoveryService discoveryService = new DiscoveryService();
        discoveryService.setRegTime(strDate)
                .setStatus("up")
                .setHost(host)
                .setPort(port)
                .setName(name)
                .setLastHeartBeat(discoveryService.getRegTime());
        HashSet<DiscoveryService> discoveryServices = new HashSet<>();
        discoveryServices.add(discoveryService);
        RemoteServiceManager.services.merge(name, discoveryServices, (oldValue, newValue) -> {
            oldValue.addAll(discoveryServices);
            return oldValue;
        });
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime ldt2 = LocalDateTime.parse(discoveryService.getLastHeartBeat(), df);
            if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - ldt2.toEpochSecond(ZoneOffset.UTC) > 10000) {
                discoveryService.setStatus("down");
            } else if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - ldt2.toEpochSecond(ZoneOffset.UTC) > 15000) {
                RemoteServiceManager.services.remove(discoveryService);
            }
        }, 10, 10, TimeUnit.SECONDS);
        return discoveryService;
    }

    public boolean remove(String name, String host, String port) {
        Map<String, Set<DiscoveryService>> services = RemoteServiceManager.services;
        Set<DiscoveryService> discoveryServices = services.get(name);
        DiscoveryService removeInstance = new DiscoveryService().setHost(host).setPort(port).setName(name);
        return discoveryServices.remove(removeInstance);
    }

    public DiscoveryService refresh(String name, String host, String port) {
        DiscoveryService instance = RemoteServiceManager.getInstance(name, host, port);
        if(instance==null){
            return null;
        }
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        instance.setLastHeartBeat(strDate)
                .setStatus("up");
        return instance;
    }
}
