package com.ognice.register.api;

import com.ognice.common.CommonResp;
import com.ognice.manager.ManagerCenter;
import com.ognice.module.DiscoveryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * <p>Title: RegisterController</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@RestController
public class RegisterController {
    @PostMapping("/register")
    public CommonResp register(String serviceName, String port, String host) {
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DiscoveryService discoveryService = new DiscoveryService();
        discoveryService.setRegTime(strDate)
                .setStatus("up")
                .setHost(host)
                .setPort(port)
                .setName(serviceName)
                .setLastHeartBeat(discoveryService.getRegTime());
        HashSet<DiscoveryService> discoveryServices = new HashSet<>();
        discoveryServices.add(discoveryService);
        ManagerCenter.services.merge(serviceName, discoveryServices, (oldValue, newValue) -> {
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
                ManagerCenter.services.remove(discoveryService);
            }
        }, 10, 10, TimeUnit.SECONDS);
        return CommonResp.success(discoveryService);
    }

    @RequestMapping("/disregister")
    public CommonResp disregister(String serviceName, String port, String host) {
        Map<String, Set<DiscoveryService>> services = ManagerCenter.services;
        Set<DiscoveryService> discoveryServices = services.get(serviceName);
        DiscoveryService removeInstance = new DiscoveryService().setHost(host).setPort(port).setName(serviceName);
        boolean remove = discoveryServices.remove(removeInstance);
        return remove ? CommonResp.success(removeInstance) : CommonResp.fail(removeInstance);
    }

    @RequestMapping("/heartbeat")
    public CommonResp heartbeat(String serviceName, String port, String host) {
        Map<String, Set<DiscoveryService>> services = ManagerCenter.services;
        Set<DiscoveryService> discoveryServices = services.get(serviceName);
        DiscoveryService updated = null;
        for (DiscoveryService instance : discoveryServices) {
            if (instance.getName().equals(serviceName) && instance.getHost().equals(host) && instance.getPort().equals(port)) {
                LocalDateTime ldt = LocalDateTime.now();
                String strDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                instance.setLastHeartBeat(strDate)
                        .setStatus("up");
                updated = instance;
                break;
            }

        }
        return updated == null ? CommonResp.fail(null) : CommonResp.success(updated);
    }
}
