package com.ognice.register.api;

import com.ognice.common.CommonResp;
import com.ognice.module.DiscoveryService;
import com.ognice.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public CommonResp register(@RequestBody DiscoveryService discoveryService) {
        return CommonResp.success(registerService.reg(discoveryService.getName(), discoveryService.getHost(),discoveryService.getPort()));
    }

    @RequestMapping("/disregister")
    public CommonResp disregister(@RequestBody DiscoveryService discoveryService) {
        boolean remove = registerService.remove(discoveryService.getName(), discoveryService.getHost(),discoveryService.getPort());
        return remove ? CommonResp.success(null) : CommonResp.fail(null);
    }

    @RequestMapping("/heartbeat")
    public CommonResp heartbeat(@RequestBody DiscoveryService discoveryService) {
        return CommonResp.success(registerService.refresh(discoveryService.getName(), discoveryService.getHost(),discoveryService.getPort()));
    }
}
