package com.ognice.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>Title: CommonResp</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@Data
@Accessors(chain = true)
public class CommonResp {
    private int code;
    private String msg;
    private Object data;

    public static CommonResp success(Object data) {
        return new CommonResp().setData(data).setCode(0).setMsg("success");
    }

    public static CommonResp fail(Object data) {
        return new CommonResp().setData(data).setCode(-1).setMsg("fail");
    }
}
