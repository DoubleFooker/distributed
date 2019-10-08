package com.ognice.module;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ServiceManager</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
public class ServiceManager {
    public static Map<String, List<String>> services = Collections.synchronizedMap(new HashMap<String, List<String>>());
}
