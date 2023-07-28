package com.demo.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info =
        @Info(
            contact =
                @Contact(name = "Arun Gupta", email = "contact@arun", url = "www.arungupta.com"),
            title = "Title for Open API",
            description = "Description for Open API",
            version = "1.0",
            license = @License(name = "MIT License", url = "www.mitlicense.com"),
            termsOfService = "Terms of service"),
    servers = {
      @Server(description = "Local ENV", url = "http://localhost:8080/kameti"),
      @Server(description = "Prod ENV", url = "http://kameti-prod.com")
    },
    security = @SecurityRequirement(name = "bearerAuth"))
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT Auth Description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER)
public class OpenApiConfiguration {}
