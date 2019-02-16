package com.tensquare.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/16 0016
 */
@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args){
        SpringApplication.run(SearchApplication.class);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
