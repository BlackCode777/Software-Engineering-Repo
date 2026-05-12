package br.com.archsoft.constructor;

import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.domain.OrderItem;
import br.com.archsoft.constructor.domain.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDomainTests {

	@Test
	void createsOrderWithCalculatedTotal() {
		Order order = Order.create(UUID.randomUUID(), List.of(
				new OrderItem(UUID.randomUUID(), 2, new BigDecimal("10.50")),
				new OrderItem(UUID.randomUUID(), 1, new BigDecimal("5.00"))
		));

		assertThat(order.id()).isNotNull();
		assertThat(order.status()).isEqualTo(OrderStatus.CREATED);
		assertThat(order.total()).isEqualByComparingTo("26.00");
		assertThat(order.createdAt()).isNotNull();
	}
}
