package br.com.archsoft.common.observability;

public final class TelemetryAttributes {
    public static final String SERVICE_NAME = "archsoft-hexagonal";
    public static final String ARCHITECTURE = "hexagonal";
    public static final String SCENARIO = "orders-mvp";
    public static final String ATTR_RUN_ID = "archsoft.experiment.run_id";
    public static final String ATTR_CHANGE_ID = "archsoft.change_id";
    public static final String ATTR_REQUEST_ID = "archsoft.request_id";
    public static final String ATTR_CONSTRUCTOR_COMMIT = "archsoft.constructor.commit";
    private TelemetryAttributes() {}
}
