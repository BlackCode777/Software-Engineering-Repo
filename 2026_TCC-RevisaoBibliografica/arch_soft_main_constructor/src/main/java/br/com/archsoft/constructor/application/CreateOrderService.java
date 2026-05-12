package br.com.archsoft.constructor.application;

import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.domain.OrderItem;
import br.com.archsoft.constructor.domain.event.OrderCreatedEvent;
import br.com.archsoft.constructor.observability.SpanNames;
import br.com.archsoft.constructor.observability.TelemetryAttributes;
import br.com.archsoft.constructor.observability.TraceHelper;
import br.com.archsoft.constructor.ports.in.CreateOrderUseCase;
import br.com.archsoft.constructor.ports.out.OrderEventPublisherPort;
import br.com.archsoft.constructor.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CreateOrderService implements CreateOrderUseCase {

	private final OrderRepositoryPort repository;
	private final OrderEventPublisherPort publisher;
	private final TraceHelper traceHelper;

	public CreateOrderService(OrderRepositoryPort repository, OrderEventPublisherPort publisher, TraceHelper traceHelper) {
		this.repository = repository;
		this.publisher = publisher;
		this.traceHelper = traceHelper;
	}

	@Override
	public Order create(UUID customerId, List<OrderItem> items) {
		return traceHelper.inSpan(SpanNames.ORDERS_CREATE,
				Map.of(TelemetryAttributes.ATTR_HTTP_ROUTE, "POST /orders"), () -> {
					Order order = Order.create(customerId, items);
					Order saved = traceHelper.inSpan(SpanNames.ORDERS_REPOSITORY_SAVE, orderTags(order),
							() -> repository.save(order));
					OrderCreatedEvent event = OrderCreatedEvent.from(saved.id(), saved.customerId(), saved.total());
					traceHelper.inSpan(SpanNames.ORDERS_EVENT_PUBLISH, orderTags(saved), () -> publisher.publish(event));
					return saved;
				});
	}

	private Map<String, String> orderTags(Order order) {
		return Map.of(
				TelemetryAttributes.ATTR_ORDER_ID, order.id().toString(),
				TelemetryAttributes.ATTR_ORDER_STATUS, order.status().name(),
				TelemetryAttributes.ATTR_ORDER_TOTAL, order.total().toPlainString()
		);
	}
}
