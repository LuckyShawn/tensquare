package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import utils.IdWorker;

@SpringBootApplication
public class TensquareBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(TensquareBaseApplication.class, args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}

