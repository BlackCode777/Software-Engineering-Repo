package br.com.archsoft.hexagonal.api;

import br.com.archsoft.common.observability.TelemetryAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "arch", TelemetryAttributes.ARCHITECTURE,
                "service", TelemetryAttributes.SERVICE_NAME);
    }
}
