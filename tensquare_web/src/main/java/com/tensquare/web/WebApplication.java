package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * @Description TODO
 * @Author shawn
 * @create 2019/2/25 0025
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class WebApplication {
    public static void main(String[] args){
      SpringApplication.run(WebApplication.class);
    }
}
