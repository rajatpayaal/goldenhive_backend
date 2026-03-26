package com.goldenhive.backend.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GoldenHive Travel APIs")
                        .description("Travel booking API documentation. Use /api/auth/login or /api/auth/admin/login to get a JWT, then click Authorize and paste only the raw token value.")
                        .version("1.0"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .tags(List.of(
                        new Tag().name("AUTH APIs (4)").description("POST /api/auth/register, POST /api/auth/login, POST /api/auth/admin/login, GET /api/auth/profile"),
                        new Tag().name("PACKAGE APIs (7)").description("Admin and user package endpoints"),
                        new Tag().name("ACTIVITY APIs (6)").description("Admin activity endpoints and user package activities"),
                        new Tag().name("PACKAGE-ACTIVITY MAPPING APIs (3)").description("Package activity mapping endpoints"),
                        new Tag().name("CART APIs (4)").description("User cart endpoints"),
                        new Tag().name("BOOKING APIs (7)").description("Booking creation and admin booking endpoints"),
                        new Tag().name("CRM / NOTES APIs (2)").description("Booking notes endpoints"),
                        new Tag().name("CUSTOM PACKAGE APIs (6)").description("Custom package request endpoints"),
                        new Tag().name("NOTIFICATION APIs (1)").description("Admin notification endpoint"),
                        new Tag().name("DASHBOARD APIs (1)").description("Admin dashboard stats endpoint"),
                        new Tag().name("REVIEW APIs (2)").description("Review endpoints"),
                        new Tag().name("PDF APIs (1)").description("Booking PDF endpoint")
                ));
    }
}
