package com.basicbug.messenger.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author JaewonChoi
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(swaggerInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.basicbug.messenger.controller"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("BasicBug API Documentation")
            .description("BasicBug Messenger API Docs")
            .license("BasicBug")
            .version("1")
            .build();
    }

}
