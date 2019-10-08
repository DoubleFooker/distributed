package com.ognice.register.api;

import com.ognice.common.CommonResp;
import com.ognice.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: RegisterController</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public CommonResp register(String serviceName, String host, String port) {
        return CommonResp.success(registerService.reg(serviceName, host, port));
    }

    @RequestMapping("/disregister")
    public CommonResp disregister(String serviceName, String host, String port) {
        boolean remove = registerService.remove(serviceName, host, port);
        return remove ? CommonResp.success(null) : CommonResp.fail(null);
    }

    @RequestMapping("/heartbeat")
    public CommonResp heartbeat(String serviceName, String port, String host) {
        return CommonResp.success(registerService.refresh(serviceName, host, port));
    }
}
