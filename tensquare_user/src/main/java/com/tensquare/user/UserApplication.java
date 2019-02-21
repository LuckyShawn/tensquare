package com.tensquare.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.tensquare.util.IdWorker;
import com.tensquare.util.InterceptorUtil;
import com.tensquare.util.JwtUtil;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tensquare"})  //如果需要跨模块注入其他模块的bean,需要制定包扫描路径
public class UserApplication {

    public static void main(String[] args){
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
