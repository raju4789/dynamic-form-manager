package com.finclutech.dynamic_form_manager.config.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Service form manager",
                version = "1.0.0",
                description = "Service form manager APIs",
                contact = @Contact(
                        name = "Raju MLN",
                        email = "narasimha4789@gmail.com",
                        url = "https://www.linkedin.com/in/raju-m-l-n/"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Production ENV",
                        url = ""
                )
        }
)
@Configuration
public class OpenApiConfig {}
