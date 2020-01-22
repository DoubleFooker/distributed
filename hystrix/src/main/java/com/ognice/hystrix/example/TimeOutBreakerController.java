package com.ognice.hystrix.example;

import com.ognice.hystrix.command.BreakerExcutorService;
import com.ognice.hystrix.command.RequestBreakerCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequestMapping("/breaker")
@RestController
public class TimeOutBreakerController {

    @RequestMapping("/demo")
    public String breakerDemo() {
        RequestBreakerCommand randomCommand = new RequestBreakerCommand();
        ThreadPoolExecutor excutor = BreakerExcutorService.getExcutor();
        Future<String> future = excutor.submit(randomCommand::run);
        String result = null;
        try {
            result = future.get(100, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // 超市/异常那，熔断
            result = randomCommand.fallBack();
        }
        return result;
    }
}
