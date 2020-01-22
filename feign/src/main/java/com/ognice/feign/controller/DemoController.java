package com.ognice.feign.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class DemoController {
    @RequestMapping("/getName")
    public String getName(String name) {
        return "demo" + name;
    }
}
