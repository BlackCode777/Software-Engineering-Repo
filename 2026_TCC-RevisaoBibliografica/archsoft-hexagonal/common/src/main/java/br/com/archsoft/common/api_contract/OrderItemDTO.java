package br.com.archsoft.common.api_contract;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDTO(UUID productId, int quantity, BigDecimal unitPrice) {
}
