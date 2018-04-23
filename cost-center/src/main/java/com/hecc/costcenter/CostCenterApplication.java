package com.hecc.costcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.hecc.costcenter.mapper")
@EnableFeignClients
@EnableEurekaClient
public class CostCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CostCenterApplication.class, args);
	}
}
