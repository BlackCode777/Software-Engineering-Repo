package br.com.archsoft.constructor.api.mapper;

import br.com.archsoft.constructor.api.dto.CreateOrderRequest;
import br.com.archsoft.constructor.api.dto.OrderItemDTO;
import br.com.archsoft.constructor.api.dto.OrderResponse;
import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

	public List<OrderItem> toDomainItems(CreateOrderRequest request) {
		return request.items().stream()
				.map(item -> new OrderItem(item.productId(), item.quantity(), item.unitPrice()))
				.toList();
	}

	public OrderResponse toResponse(Order order) {
		return new OrderResponse(
				order.id(),
				order.customerId(),
				order.status(),
				order.total(),
				order.createdAt(),
				toDtoItems(order.items())
		);
	}

	private List<OrderItemDTO> toDtoItems(List<OrderItem> items) {
		return items.stream()
				.map(item -> new OrderItemDTO(item.productId(), item.quantity(), item.unitPrice()))
				.toList();
	}
}
