package br.com.archsoft.common.ports.in;

import br.com.archsoft.common.domain.Order;
import br.com.archsoft.common.domain.OrderItem;
import java.util.List;
import java.util.UUID;

public interface CreateOrderUseCase {
    Order create(UUID customerId, List<OrderItem> items);
}
