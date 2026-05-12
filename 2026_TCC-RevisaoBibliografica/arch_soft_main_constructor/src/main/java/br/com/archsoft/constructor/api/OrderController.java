package br.com.archsoft.constructor.api;

import br.com.archsoft.constructor.api.dto.CreateOrderRequest;
import br.com.archsoft.constructor.api.dto.OrderResponse;
import br.com.archsoft.constructor.api.mapper.OrderMapper;
import br.com.archsoft.constructor.domain.Order;
import br.com.archsoft.constructor.ports.in.CreateOrderUseCase;
import br.com.archsoft.constructor.ports.in.GetOrderByIdUseCase;
import br.com.archsoft.constructor.ports.in.ListOrdersUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Constructor contract for the Orders MVP")
public class OrderController {

	private final CreateOrderUseCase createOrderUseCase;
	private final GetOrderByIdUseCase getOrderByIdUseCase;
	private final ListOrdersUseCase listOrdersUseCase;
	private final OrderMapper mapper;

	public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderByIdUseCase getOrderByIdUseCase,
			ListOrdersUseCase listOrdersUseCase, OrderMapper mapper) {
		this.createOrderUseCase = createOrderUseCase;
		this.getOrderByIdUseCase = getOrderByIdUseCase;
		this.listOrdersUseCase = listOrdersUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create an order with items")
	public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
		Order order = createOrderUseCase.create(request.customerId(), mapper.toDomainItems(request));
		return mapper.toResponse(order);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get an order by ID")
	public OrderResponse getById(@PathVariable UUID id) {
		return mapper.toResponse(getOrderByIdUseCase.getById(id));
	}

	@GetMapping
	@Operation(summary = "List orders with pagination")
	public Page<OrderResponse> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100));
		return listOrdersUseCase.list(pageable).map(mapper::toResponse);
	}
}
