package br.com.archsoft.common.observability;

public final class CorrelationHeaders {
    public static final String REQUEST_ID = "X-Request-Id";
    public static final String RUN_ID = "X-Experiment-Run-Id";
    public static final String CHANGE_ID = "X-Change-Id";
    public static final String ARCHITECTURE = "X-Architecture";
    public static final String CONSTRUCTOR_COMMIT = "X-Constructor-Commit";
    private CorrelationHeaders() {}
}
