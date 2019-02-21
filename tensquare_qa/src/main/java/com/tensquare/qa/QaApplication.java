package com.tensquare.qa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import com.tensquare.util.IdWorker;
import com.tensquare.util.JwtUtil;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tensquare"})  //如果需要跨模块注入其他模块的bean,需要制定包扫描路径
public class QaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QaApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public JwtUtil jwtUtil(){
		return new JwtUtil();
	}

}
