package com.ognice.manager;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: ConfManager</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/24
 */
@Data
@Accessors(chain = true)
public class ConfManager {
    /**
     * serviceName,confs
     */
    public final static Map<String, ConfTracking> SERVICE_CONF_DATA_POOL = new ConcurrentHashMap<>();
}
