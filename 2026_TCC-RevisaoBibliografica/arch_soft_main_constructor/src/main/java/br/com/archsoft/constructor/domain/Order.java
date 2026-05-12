package br.com.archsoft.constructor.domain;

import br.com.archsoft.constructor.domain.exception.DomainValidationException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

	private final UUID id;
	private final UUID customerId;
	private final List<OrderItem> items;
	private final BigDecimal total;
	private final OrderStatus status;
	private final OffsetDateTime createdAt;

	private Order(UUID id, UUID customerId, List<OrderItem> items, OrderStatus status, OffsetDateTime createdAt) {
		if (id == null) {
			throw new DomainValidationException("id is required");
		}
		if (customerId == null) {
			throw new DomainValidationException("customerId is required");
		}
		if (items == null || items.isEmpty()) {
			throw new DomainValidationException("order must contain at least one item");
		}
		if (status == null) {
			throw new DomainValidationException("status is required");
		}
		if (createdAt == null) {
			throw new DomainValidationException("createdAt is required");
		}

		this.id = id;
		this.customerId = customerId;
		this.items = List.copyOf(items);
		this.total = calculateTotal(this.items);
		this.status = status;
		this.createdAt = createdAt;
	}

	public static Order create(UUID customerId, List<OrderItem> items) {
		return new Order(UUID.randomUUID(), customerId, items, OrderStatus.CREATED, OffsetDateTime.now());
	}

	public static Order restore(UUID id, UUID customerId, List<OrderItem> items, OrderStatus status,
			OffsetDateTime createdAt) {
		return new Order(id, customerId, items, status, createdAt);
	}

	private static BigDecimal calculateTotal(List<OrderItem> items) {
		return items.stream()
				.map(OrderItem::total)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public UUID id() {
		return id;
	}

	public UUID customerId() {
		return customerId;
	}

	public List<OrderItem> items() {
		return items;
	}

	public BigDecimal total() {
		return total;
	}

	public OrderStatus status() {
		return status;
	}

	public OffsetDateTime createdAt() {
		return createdAt;
	}
}
