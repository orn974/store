package com.store.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.store.model")
@SpringBootApplication
public class WebApp {
    public static void main( String[] args )
    {
        SpringApplication.run(WebApp.class, args);

    }
}
