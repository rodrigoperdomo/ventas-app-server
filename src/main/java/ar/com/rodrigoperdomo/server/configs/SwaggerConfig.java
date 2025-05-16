package ar.com.rodrigoperdomo.server.configs;

import static ar.com.rodrigoperdomo.server.utils.Constantes.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(new Info().title(TITLE_API).version(VERSION_API).description(DESCRIPTION_API_SWAGGER))
        .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_STRING))
        .components(
            new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(
                    BEARER_AUTH_STRING,
                    new SecurityScheme()
                        .name(BEARER_AUTH_STRING)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(BEARER_STRING)
                        .bearerFormat(JWT_STRING)));
  }
}
