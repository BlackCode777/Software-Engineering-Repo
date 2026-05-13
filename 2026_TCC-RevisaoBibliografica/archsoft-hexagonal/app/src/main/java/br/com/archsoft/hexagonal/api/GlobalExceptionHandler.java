package br.com.archsoft.hexagonal.api;

import br.com.archsoft.common.observability.CorrelationContext;
import br.com.archsoft.common.observability.TelemetryAttributes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problem.setType(URI.create("https://archsoft.local/problems/internal-error"));
        problem.setTitle("Internal Server Error");
        problem.setProperty("code", "ARCHSOFT_INTERNAL_ERROR");
        CorrelationContext context = (CorrelationContext) request.getAttribute(CorrelationHeaderFilter.CONTEXT_ATTRIBUTE);
        problem.setProperty("requestId", context == null ? null : context.requestId());
        problem.setProperty("runId", context == null ? null : context.runId());
        problem.setProperty("changeId", context == null ? null : context.changeId());
        problem.setProperty("architecture", context == null ? TelemetryAttributes.ARCHITECTURE : context.architecture());
        problem.setProperty("constructorCommit", context == null ? null : context.constructorCommit());
        return problem;
    }
}
