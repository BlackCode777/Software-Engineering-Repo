package br.com.archsoft.common.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Order(UUID id, UUID customerId, OrderStatus status, BigDecimal total, OffsetDateTime createdAt,
                    List<OrderItem> items) {
    public static Order create(UUID customerId, List<OrderItem> items) {
        BigDecimal total = items.stream().map(OrderItem::total).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Order(UUID.randomUUID(), customerId, OrderStatus.CREATED, total, OffsetDateTime.now(), List.copyOf(items));
    }
}
