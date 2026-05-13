package br.com.archsoft.common.observability;

public record CorrelationContext(String requestId, String runId, String changeId, String architecture,
                                 String constructorCommit) {
}
