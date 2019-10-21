package com.ognice.client;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: LocalServicesManager</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/21
 */
@Data
@Accessors(chain = true)
public class LocalServicesManager {
    public static Map<String, List<String>> localServices = Collections.synchronizedMap(new HashMap<>());
}
