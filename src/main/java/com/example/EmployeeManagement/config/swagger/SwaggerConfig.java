package com.example.EmployeeManagement.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// http://localhost:8080/swagger-ui/index.html#/
// /v3/api-docs

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI employeeMangementOpenAPI() {

        String schemeName="bearerScheme";

        return new OpenAPI()
                .info(new Info().title("Employee Management Api")
                        .description("Employee Management sample application")
                        .version("v0.0.1")
                        .contact(new Contact().name("Tabish").email("muhammadtabish05@gmail.com").url("https://www.linkedin.com/in/muhammad-tabish-rashid-392b3a1a5/"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName,new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Employee Management Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}