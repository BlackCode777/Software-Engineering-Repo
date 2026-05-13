package br.com.archsoft.common.api_contract;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(UUID customerId, List<OrderItemDTO> items) {
}
