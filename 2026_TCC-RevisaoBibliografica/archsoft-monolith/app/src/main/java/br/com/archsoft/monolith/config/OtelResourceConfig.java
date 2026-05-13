package br.com.archsoft.monolith.config;

import br.com.archsoft.common.observability.TelemetryAttributes;
import io.opentelemetry.sdk.resources.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtelResourceConfig {
    @Bean
    Resource otelResource() {
        return Resource.getDefault().toBuilder()
                .put("service.name", TelemetryAttributes.SERVICE_NAME)
                .put("archsoft.architecture", TelemetryAttributes.ARCHITECTURE)
                .put("archsoft.scenario", TelemetryAttributes.SCENARIO)
                .put("archsoft.repo", "archsoft-monolith")
                .put("deployment.environment", "local")
                .build();
    }
}
