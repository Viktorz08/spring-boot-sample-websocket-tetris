package com.hotmail.viktorz08.test.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@ComponentScan("com.hotmail.viktorz08.test.spring")
public class SimpleController {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleController.class);

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{SimpleController.class}, args);
    }

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World";
    }
}