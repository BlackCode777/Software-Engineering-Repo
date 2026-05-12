package br.com.archsoft.constructor.domain.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderCreatedEvent(
		UUID eventId,
		UUID orderId,
		UUID customerId,
		BigDecimal total,
		OffsetDateTime occurredAt
) {

	public static OrderCreatedEvent from(UUID orderId, UUID customerId, BigDecimal total) {
		return new OrderCreatedEvent(UUID.randomUUID(), orderId, customerId, total, OffsetDateTime.now());
	}
}
