package com.example.SpaceshipW2M.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("spaceship-api")
				.pathsToMatch("/**")
				.build();
	}

	@Bean
	public Info apiInfo() {
		return new Info()
				.title("Spaceship API")
				.description("API for managing spaceships")
				.version("1.0.0");
	}
}