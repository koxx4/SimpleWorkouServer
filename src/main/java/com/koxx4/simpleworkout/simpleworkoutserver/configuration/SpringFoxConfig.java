package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    public static final String LOGIN_CONTROLLER_TAG = "Login Controller";
    public static final String REGISTRATION_CONTROLLER = "Registration Controller";
    public static final String USER_ACTIONS_CONTROLLER = "User Actions Controller / User Data Controller";

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.koxx4.simpleworkout.simpleworkoutserver.controllers"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(LOGIN_CONTROLLER_TAG, "Authorizes and generates JWS tokens"))
                .tags(new Tag(REGISTRATION_CONTROLLER, "Validates and saves user identification information to database"))
                .tags(new Tag(USER_ACTIONS_CONTROLLER, "Enables clients with valid JWS tokens to obtain and manipulate their data"))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder().title("Simple workout API").version("2.0.0").build();
    }
}
