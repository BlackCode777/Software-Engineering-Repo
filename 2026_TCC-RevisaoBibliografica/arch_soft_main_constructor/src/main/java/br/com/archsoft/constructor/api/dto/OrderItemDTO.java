package br.com.archsoft.constructor.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDTO(
		@NotNull UUID productId,
		@Min(1) int quantity,
		@NotNull @PositiveOrZero BigDecimal unitPrice
) {
}
