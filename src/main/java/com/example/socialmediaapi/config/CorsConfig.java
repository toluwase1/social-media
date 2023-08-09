package com.example.socialmediaapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
//@EnableSwagger2
class SwaggerConfig  implements WebMvcConfigurer {
    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.example.socialmediaapi.controller"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiDetails())
                .securitySchemes(securitySchemes()).securityContexts(List.of(securityContext()));
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private List<SecurityScheme> securitySchemes() {
        return List.of(new ApiKey("Bearer", "Authorization", "header"));
    }
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(List.of(bearerAuthReference())).forPaths(PathSelectors.any()).build();
    }
    private SecurityReference bearerAuthReference() {
        return new SecurityReference("Bearer", new AuthorizationScope[0]);
    }
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Human Resource System", " Human Resource API model", "1.0", "Free to use",
                new springfox.documentation.service.Contact("Human Resource App", "http://localhost:8080", "toluwasethomas1@gmail.com"),
                "API License", "http://localhost:8080",
                Collections.emptyList());
    }

}