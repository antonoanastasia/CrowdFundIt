package com.example.__2021142_2022002.config;

//Annotations για Swagger/OpenAPI τεκμηρίωση και ρύθμιση ασφάλειας
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

//Η κλάση είναι configuration class του Spring
@Configuration

//Ορισμός βασικών πληροφοριών για το OpenAPI και καθολική απαίτηση authentication
@OpenAPIDefinition(
        info = @Info(title = "Crowdfunding API", version = "1.0"),
        security = @SecurityRequirement(name = "basicAuth")
)

//Ορισμός σχήματος ασφαλείας που θα χρησιμοποιεί η Swagger τεκμηρίωση
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig {
    //ρυθμιστικό αρχείο για την OpenAPI τεκμηρίωση και ασφάλεια.
}
