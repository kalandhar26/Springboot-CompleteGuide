package com.spring.mykafka.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .version("1.0")
                        .description("API Documentation")
                        .contact(new Contact()
                                .name("Support")
                                .email("support@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("More documentation")
                        .url("https://example.com/docs"));
    }
}
