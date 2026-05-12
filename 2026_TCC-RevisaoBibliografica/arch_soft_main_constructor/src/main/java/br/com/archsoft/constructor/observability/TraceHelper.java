package br.com.archsoft.constructor.observability;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Supplier;

@Component
public class TraceHelper {

	private final Tracer tracer;

	public TraceHelper(Tracer tracer) {
		this.tracer = tracer;
	}

	public <T> T inSpan(String name, Map<String, String> tags, Supplier<T> supplier) {
		Span span = tracer.nextSpan().name(name).start();
		tags.forEach(span::tag);
		span.tag(TelemetryAttributes.ATTR_ARCHITECTURE, TelemetryAttributes.ARCHITECTURE);
		span.tag(TelemetryAttributes.ATTR_SCENARIO, TelemetryAttributes.SCENARIO);
		span.tag(TelemetryAttributes.ATTR_REPO, TelemetryAttributes.REPO);
		try (Tracer.SpanInScope ignored = tracer.withSpan(span)) {
			return supplier.get();
		}
		catch (RuntimeException ex) {
			span.error(ex);
			throw ex;
		}
		finally {
			span.end();
		}
	}

	public void inSpan(String name, Map<String, String> tags, Runnable runnable) {
		inSpan(name, tags, () -> {
			runnable.run();
			return null;
		});
	}
}
