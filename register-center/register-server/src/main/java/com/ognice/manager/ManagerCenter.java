package com.ognice.manager;

import com.ognice.module.DiscoveryService;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: ManagerCenter</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@Data
@Accessors(chain = true)
public class ManagerCenter {
    public static Map<String, Set<DiscoveryService>> services=new ConcurrentHashMap<>();
}
