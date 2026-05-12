package br.com.archsoft.constructor.observability;

public final class SpanNames {

	public static final String ORDERS_CREATE = "orders.create";
	public static final String ORDERS_GET_BY_ID = "orders.getById";
	public static final String ORDERS_LIST = "orders.list";
	public static final String ORDERS_REPOSITORY_SAVE = "orders.repository.save";
	public static final String ORDERS_REPOSITORY_FIND_BY_ID = "orders.repository.findById";
	public static final String ORDERS_REPOSITORY_FIND_ALL = "orders.repository.findAll";
	public static final String ORDERS_EVENT_PUBLISH = "orders.event.publish";

	private SpanNames() {
	}
}
