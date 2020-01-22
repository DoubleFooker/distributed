package com.ognice.feign.service;

import com.ognice.feign.annotation.CustomFeignClient;
import com.ognice.feign.annotation.GetRequest;

@CustomFeignClient(url = "http://localhost:8080", fallback = "demoFallBack")
public interface FeignService {
    @GetRequest(path = "/demo/getName")
    String getUser(String name);
}
