package com.hahn.myemployees.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI myEmployeesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyEmployees API")
                        .description("Employee Management System API")
                        .version("1.0"));
    }
}
