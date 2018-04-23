package com.hecc.costcenter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author xuhoujun
 * @description: swagger配置
 * @date: Created In 下午8:55 on 2018/4/22.
 */
@Configuration
public class Swagger2Config {

    @Value("${cost-center-swagger2.hostname}")
    private String hostName;

    @Value("${cost-center-swagger2.isEnable}")
    private boolean isEnable;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(isEnable)
                .apiInfo(apiInfo()).host(hostName).select()
                .apis(RequestHandlerSelectors.basePackage("com.hecc.costcenter"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("费用中心业务中心APIS")
                .description("具体内容详见：http://xuhoujun.com")
                .termsOfServiceUrl("http://xuhoujun.com")
                .version("1.0").build();
    }
}
