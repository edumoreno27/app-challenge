package com.pruebatecnica.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.util.UriComponentsBuilder;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.DefaultPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Import(BeanValidatorPluginsConfiguration.class)


public class SwaggerConfig {	
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("evaluacion-api")
        		.host("localhost:8080")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pruebatecnica.api.controller"))
                .paths(PathSelectors.ant("/**"))                
                .build();
    }
 
 	
 	@Bean
 	public ApiInfo apiInfo() {
 	    return new ApiInfoBuilder()
 	        .title("API Evaluación")
 	        .description("API Evaluación")
 	        .version("1.1")
 	        .build();
 	} 	
 	
 
}
