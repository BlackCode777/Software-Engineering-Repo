package br.com.archsoft.constructor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "management.otlp.tracing.endpoint=http://localhost:4318/v1/traces")
class ArchSoftMainConstructorApplicationTests {

	@Test
	void contextLoads() {
	}
}
