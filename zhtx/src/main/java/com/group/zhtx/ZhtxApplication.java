package com.group.zhtx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class ZhtxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhtxApplication.class, args);
    }
}
