package com.ognice.feign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
@Configuration
@Import(CustomFeignRegistry.class)
public class AutoFeignConfig  {

}
