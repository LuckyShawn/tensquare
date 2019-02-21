package com.tensquare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import util.IdWorker;
import util.InterceptorUtil;
import util.JwtUtil;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.tensquare.interceptor","com.tensquare"})
public class UserApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

    //加密密码
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }

    @Bean
    public InterceptorUtil interceptorUtil(){
        return new InterceptorUtil();
    }

}
