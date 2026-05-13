package br.com.archsoft.cqrs.api;

import br.com.archsoft.common.observability.CorrelationContext;
import br.com.archsoft.common.observability.CorrelationHeaders;
import br.com.archsoft.common.observability.TelemetryAttributes;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CorrelationHeaderFilter extends OncePerRequestFilter {
    public static final String CONTEXT_ATTRIBUTE = "archsoft.correlation.context";
    private final Tracer tracer;

    public CorrelationHeaderFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String requestId = Optional.ofNullable(request.getHeader(CorrelationHeaders.REQUEST_ID))
                .filter(value -> !value.isBlank()).orElseGet(() -> UUID.randomUUID().toString());
        CorrelationContext context = new CorrelationContext(requestId, blankToNull(request.getHeader(CorrelationHeaders.RUN_ID)),
                blankToNull(request.getHeader(CorrelationHeaders.CHANGE_ID)),
                Optional.ofNullable(blankToNull(request.getHeader(CorrelationHeaders.ARCHITECTURE))).orElse(TelemetryAttributes.ARCHITECTURE),
                blankToNull(request.getHeader(CorrelationHeaders.CONSTRUCTOR_COMMIT)));
        request.setAttribute(CONTEXT_ATTRIBUTE, context);
        response.setHeader(CorrelationHeaders.REQUEST_ID, context.requestId());
        setIfPresent(response, CorrelationHeaders.RUN_ID, context.runId());
        setIfPresent(response, CorrelationHeaders.CHANGE_ID, context.changeId());
        try {
            MDC.put("requestId", context.requestId());
            putIfPresent("runId", context.runId());
            putIfPresent("changeId", context.changeId());
            enrichCurrentSpan(context);
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private void enrichCurrentSpan(CorrelationContext context) {
        Span span = tracer.currentSpan();
        if (span == null) return;
        span.tag(TelemetryAttributes.ATTR_REQUEST_ID, context.requestId());
        tagIfPresent(span, TelemetryAttributes.ATTR_RUN_ID, context.runId());
        tagIfPresent(span, TelemetryAttributes.ATTR_CHANGE_ID, context.changeId());
        tagIfPresent(span, TelemetryAttributes.ATTR_CONSTRUCTOR_COMMIT, context.constructorCommit());
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private static void setIfPresent(HttpServletResponse response, String header, String value) {
        if (value != null) response.setHeader(header, value);
    }

    private static void putIfPresent(String key, String value) {
        if (value != null) MDC.put(key, value);
    }

    private static void tagIfPresent(Span span, String key, String value) {
        if (value != null) span.tag(key, value);
    }
}
