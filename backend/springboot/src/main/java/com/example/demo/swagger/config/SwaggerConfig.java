package com.example.demo.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(true) //swagger 에서 제공해주는 기본 응답 코드 사용 여부
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any()) // api 스펙이 작성된 컨트롤러 패키지
                .paths(PathSelectors.any()) // apis 에서 특정 path 선택
                .build();
    }

    // swagger info
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Rest API")
                .description("Rest API Document")
                .version("1.0")
                .build();
    }
}
