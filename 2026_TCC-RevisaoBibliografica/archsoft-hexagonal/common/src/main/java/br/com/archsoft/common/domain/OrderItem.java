package br.com.archsoft.common.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {
    public BigDecimal total() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
