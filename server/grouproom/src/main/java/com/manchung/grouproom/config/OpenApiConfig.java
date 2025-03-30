package com.manchung.grouproom.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        servers = {
                @Server(url = "https://manchunggrouproom.duckdns.org/dev", description = "Dev Server"),
                @Server(url = "https://manchunggrouproom.duckdns.org", description = "Production Server")
        }
)
@Configuration
public class OpenApiConfig {
}
