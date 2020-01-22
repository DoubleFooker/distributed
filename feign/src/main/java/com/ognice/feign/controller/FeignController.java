package com.ognice.feign.controller;

import com.ognice.feign.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/feign")
@RestController
public class FeignController {
    @Autowired
    FeignService feignService;

    @RequestMapping("/demo")
    public String demo(String name) {
        return feignService.getUser(name);
    }
}
