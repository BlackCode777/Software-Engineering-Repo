package br.com.archsoft.constructor.ports.in;

import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.domain.OrderItem;

import java.util.List;
import java.util.UUID;

public interface CreateOrderUseCase {

	Order create(UUID customerId, List<OrderItem> items);
}
