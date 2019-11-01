package com.ognice.api;

import com.ognice.common.CommonResp;
import com.ognice.module.ConfData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: ConfApiController</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/24
 */
@RestController
@RequestMapping("/confs")
public class ConfApiController {
    @PostMapping()
    public CommonResp addConf(@RequestBody ConfData confData) {
        return null;

    }
}
