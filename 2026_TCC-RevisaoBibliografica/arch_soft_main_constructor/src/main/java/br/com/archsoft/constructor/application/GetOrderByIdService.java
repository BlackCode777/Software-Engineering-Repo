package br.com.archsoft.constructor.application;

import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.domain.exception.OrderNotFoundException;
import br.com.archsoft.constructor.observability.SpanNames;
import br.com.archsoft.constructor.observability.TelemetryAttributes;
import br.com.archsoft.constructor.observability.TraceHelper;
import br.com.archsoft.constructor.ports.in.GetOrderByIdUseCase;
import br.com.archsoft.constructor.ports.out.OrderRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class GetOrderByIdService implements GetOrderByIdUseCase {

	private final OrderRepositoryPort repository;
	private final TraceHelper traceHelper;

	public GetOrderByIdService(OrderRepositoryPort repository, TraceHelper traceHelper) {
		this.repository = repository;
		this.traceHelper = traceHelper;
	}

	@Override
	public Order getById(UUID id) {
		return traceHelper.inSpan(SpanNames.ORDERS_GET_BY_ID, Map.of(
				TelemetryAttributes.ATTR_HTTP_ROUTE, "GET /orders/{id}",
				TelemetryAttributes.ATTR_ORDER_ID, id.toString()
		), () -> traceHelper.inSpan(SpanNames.ORDERS_REPOSITORY_FIND_BY_ID,
				Map.of(TelemetryAttributes.ATTR_ORDER_ID, id.toString()),
				() -> repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id))));
	}
}
