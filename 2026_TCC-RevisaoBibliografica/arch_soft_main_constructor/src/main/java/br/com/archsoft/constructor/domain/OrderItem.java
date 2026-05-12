package br.com.archsoft.constructor.domain;

import br.com.archsoft.constructor.domain.exception.DomainValidationException;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(UUID productId, int quantity, BigDecimal unitPrice) {

	public OrderItem {
		if (productId == null) {
			throw new DomainValidationException("productId is required");
		}
		if (quantity <= 0) {
			throw new DomainValidationException("quantity must be greater than zero");
		}
		if (unitPrice == null || unitPrice.signum() < 0) {
			throw new DomainValidationException("unitPrice must be zero or positive");
		}
	}

	public BigDecimal total() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}
}
