package com.caito.waletservice.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Swagger configuration class for the Wallet Service API.
 * This class sets up the OpenAPI documentation with relevant metadata
 * and security schemes for JWT authentication.
 *
 * @author caito
 *
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Easy Pay / Wallet Service API",
                version = "1.0.0",
                description = "Wallet Service API Documentation for Easy Pay Application",
                contact = @Contact(name = "caito Vilas", email = "caitocd@gmail.com")
        )
)
@SecurityScheme(
        name = "security token",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "Bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
