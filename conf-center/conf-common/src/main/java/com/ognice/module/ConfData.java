package com.ognice.module;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Title: ConfData</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/24
 */
@Data
@Accessors(chain = true)
public class ConfData {
    private String key;
    private String val;
    private String version;
}
