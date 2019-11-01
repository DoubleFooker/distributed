package com.ognice.manager;

import com.ognice.module.ConfData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: ConfTracking</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/24
 */
@Data
@Accessors(chain = true)
public class ConfTracking {
    /**
     * 历史版本记录
     * version,conf
     */
    Map<String, List<ConfData>> configHistory;
}
