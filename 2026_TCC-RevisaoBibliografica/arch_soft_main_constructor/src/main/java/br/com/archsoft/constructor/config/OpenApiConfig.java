package br.com.archsoft.constructor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI archSoftOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("ArchSoft MAIN Constructor API")
						.version("0.0.1")
						.description("Contract for the Orders MVP used by monolith, hexagonal and CQRS experiments."));
	}
}
