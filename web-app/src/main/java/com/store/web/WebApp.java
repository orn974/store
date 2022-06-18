package com.store.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EntityScan("com.store.model")
@SpringBootApplication
public class WebApp extends SpringBootServletInitializer {
    public static void main( String[] args )
    {
        SpringApplication.run(WebApp.class, args);

    }
}
