package br.com.archsoft.common.api_contract;

import br.com.archsoft.common.domain.OrderStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(UUID id, UUID customerId, OrderStatus status, BigDecimal total, OffsetDateTime createdAt,
                            List<OrderItemDTO> items) {
}
