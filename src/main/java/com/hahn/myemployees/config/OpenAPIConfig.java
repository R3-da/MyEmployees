package com.hahn.myemployees.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenAPIConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAPIConfig.class);
    
    private final Environment environment;

    public OpenAPIConfig(Environment environment) {
        this.environment = environment;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void logSwaggerUiUrl() {
        String port = environment.getProperty("server.port", "8080");
        logger.info("Swagger UI is available at: http://localhost:{}/swagger-ui.html", port);
    }
    
    @Bean
    public OpenAPI myEmployeesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyEmployees API")
                        .description("Employee Management System API")
                        .version("1.0"));
    }
}