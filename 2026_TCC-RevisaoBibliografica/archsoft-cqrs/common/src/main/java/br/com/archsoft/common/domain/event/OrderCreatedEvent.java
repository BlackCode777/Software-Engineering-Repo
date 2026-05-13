package br.com.archsoft.common.domain.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderCreatedEvent(UUID eventId, UUID orderId, UUID customerId, BigDecimal total, OffsetDateTime occurredAt) {
}
