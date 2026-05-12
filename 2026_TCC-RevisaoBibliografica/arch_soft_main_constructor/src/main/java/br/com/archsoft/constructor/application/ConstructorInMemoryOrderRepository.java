package br.com.archsoft.constructor.application;

import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.ports.out.OrderRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConstructorInMemoryOrderRepository implements OrderRepositoryPort {

	private final Map<UUID, Order> orders = new ConcurrentHashMap<>();

	@Override
	public Order save(Order order) {
		orders.put(order.id(), order);
		return order;
	}

	@Override
	public Optional<Order> findById(UUID id) {
		return Optional.ofNullable(orders.get(id));
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		List<Order> ordered = orders.values().stream()
				.sorted(Comparator.comparing(Order::createdAt).reversed())
				.toList();
		int start = Math.min((int) pageable.getOffset(), ordered.size());
		int end = Math.min(start + pageable.getPageSize(), ordered.size());
		return new PageImpl<>(ordered.subList(start, end), pageable, ordered.size());
	}
}
