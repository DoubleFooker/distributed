package com.ognice.controller;

import com.ognice.service.DbfkRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: DemoController</p>
 * <p>Description:  </p>
 *
 * @author huangkaifu
 * @date 2019/10/8
 */
@RestController
public class DemoController {
    @Autowired
    DbfkRestService dbfkRestService;

    @RequestMapping("/zk/demo")
    public String demo() {
        return dbfkRestService.reqDemo();
    }
    @RequestMapping("/zk/service/demo")
    public String serviceDemo() {
        return "dbfk-service resp";
    }
}
