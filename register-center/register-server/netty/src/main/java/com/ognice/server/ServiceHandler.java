package com.ognice.server;

import com.alibaba.fastjson.JSON;
import com.ognice.common.ServiceRequest;
import com.ognice.module.DiscoveryService;
import com.ognice.module.ServiceManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: ServerHandler</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/12
 */
@Data
@Accessors(chain = true)
public class ServiceHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ServiceRequest serviceRequest = JSON.parseObject(msg.toString(), ServiceRequest.class);
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DiscoveryService discoveryService = serviceRequest.getDiscoveryService();
        Set<DiscoveryService> newService = new HashSet<>();
        newService.add(discoveryService);
        switch (serviceRequest.getMethod()) {
            case "disreg":
                ServiceManager.services.get(discoveryService.getName()).remove(discoveryService);
                break;
            case "heartbeat":
                DiscoveryService instance = ServiceManager.getInstance(discoveryService.getName(), discoveryService.getHost(), discoveryService.getPort());
                if (instance == null) {
                    break;
                }
                instance.setLastHeartBeat(strDate)
                        .setStatus("UP");
                break;
            case "reg":
                discoveryService.setStatus("UP")
                        .setLastHeartBeat(strDate)
                        .setRegTime(strDate);
                ServiceManager.services.merge(discoveryService.getName(), newService, (oldValue, newValue) -> {
                    oldValue.addAll(newService);
                    return oldValue;
                });

                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                scheduledExecutorService.scheduleAtFixedRate(() -> {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime ldt2 = LocalDateTime.parse(discoveryService.getLastHeartBeat(), df);
                    if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - ldt2.toEpochSecond(ZoneOffset.UTC) > 10000) {
                        discoveryService.setStatus("down");
                    } else if (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - ldt2.toEpochSecond(ZoneOffset.UTC) > 15000) {
                        ServiceManager.services.remove(discoveryService);
                    }
                }, 10, 10, TimeUnit.SECONDS);
                break;
            default:
                break;
        }
        super.channelRead(ctx, msg);
    }
}
