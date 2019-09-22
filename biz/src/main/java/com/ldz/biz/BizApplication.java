package com.ldz.biz;

import com.ldz.biz.service.UserService;
import com.ldz.util.spring.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.ldz")
public class BizApplication {

    public static void main(String[] args) {
        SpringApplication.run(BizApplication.class, args);
//        UserService userService = SpringContextUtil.getBean(UserService.class);
//        userService.initRobot();
//        System.out.println("initUsers");
    }


}
