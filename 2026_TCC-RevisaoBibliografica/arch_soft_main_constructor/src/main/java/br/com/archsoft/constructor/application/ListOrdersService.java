package br.com.archsoft.constructor.application;

import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.observability.SpanNames;
import br.com.archsoft.constructor.observability.TelemetryAttributes;
import br.com.archsoft.constructor.observability.TraceHelper;
import br.com.archsoft.constructor.ports.in.ListOrdersUseCase;
import br.com.archsoft.constructor.ports.out.OrderRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ListOrdersService implements ListOrdersUseCase {

	private final OrderRepositoryPort repository;
	private final TraceHelper traceHelper;

	public ListOrdersService(OrderRepositoryPort repository, TraceHelper traceHelper) {
		this.repository = repository;
		this.traceHelper = traceHelper;
	}

	@Override
	public Page<Order> list(Pageable pageable) {
		return traceHelper.inSpan(SpanNames.ORDERS_LIST,
				Map.of(TelemetryAttributes.ATTR_HTTP_ROUTE, "GET /orders"),
				() -> traceHelper.inSpan(SpanNames.ORDERS_REPOSITORY_FIND_ALL, Map.of(),
						() -> repository.findAll(pageable)));
	}
}
