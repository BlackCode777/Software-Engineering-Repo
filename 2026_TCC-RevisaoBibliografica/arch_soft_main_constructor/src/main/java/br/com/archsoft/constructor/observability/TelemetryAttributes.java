package br.com.archsoft.constructor.observability;

public final class TelemetryAttributes {

	public static final String SERVICE_NAME = "archsoft-constructor";
	public static final String ARCHITECTURE = "constructor";
	public static final String SCENARIO = "orders-mvp";
	public static final String REPO = "archsoft-main-constructor";

	public static final String ATTR_ARCHITECTURE = "archsoft.architecture";
	public static final String ATTR_SCENARIO = "archsoft.scenario";
	public static final String ATTR_REPO = "archsoft.repo";
	public static final String ATTR_ORDER_ID = "order.id";
	public static final String ATTR_ORDER_STATUS = "order.status";
	public static final String ATTR_ORDER_TOTAL = "order.total";
	public static final String ATTR_HTTP_ROUTE = "http.route";

	private TelemetryAttributes() {
	}
}
