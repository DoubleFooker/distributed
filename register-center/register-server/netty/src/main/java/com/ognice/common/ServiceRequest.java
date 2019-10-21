package com.ognice.common;

import com.ognice.module.DiscoveryService;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Title: Request</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/12
 */
@Data
@Accessors(chain = true)
public class ServiceRequest {
    private String method;
    private DiscoveryService discoveryService;
}
