package br.com.archsoft.constructor.api.dto;

import br.com.archsoft.constructor.domain.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
		UUID id,
		UUID customerId,
		OrderStatus status,
		BigDecimal total,
		OffsetDateTime createdAt,
		List<OrderItemDTO> items
) {
}
