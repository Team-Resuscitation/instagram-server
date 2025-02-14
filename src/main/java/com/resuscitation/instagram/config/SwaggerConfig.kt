package com.resuscitation.instagram.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "instagram clone server API",
        version = "2.0",
        description = "instagram clone server API",
        contact = Contact(name = "Yooun Chaemin", email = "cinnamein@gmail.com")
    )
)
@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val serverItems = Server().url("/")

        // Security scheme
        val jwtSchemeName = "JSON Web Token (JWT)"

        // Add auth info in API request header
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)

        // SecurityScheme for API document
        val components = Components().addSecuritySchemes(
            jwtSchemeName,
            SecurityScheme().name(jwtSchemeName).type(SecurityScheme.Type.HTTP) // HTTP 방식
                .scheme("bearer").bearerFormat("JWT")
        )

        return OpenAPI()
            .addServersItem(serverItems)
            .addSecurityItem(securityRequirement)
            .components(components)
    }
}