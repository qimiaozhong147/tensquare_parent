package com.tensquare.recruit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
@SpringBootApplication
@EnableEurekaClient
public class EnterpriseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
