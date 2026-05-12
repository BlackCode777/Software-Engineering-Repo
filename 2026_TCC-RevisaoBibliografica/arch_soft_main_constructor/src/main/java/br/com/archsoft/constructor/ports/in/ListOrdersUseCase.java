package br.com.archsoft.constructor.ports.in;

import br.com.archsoft.constructor.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListOrdersUseCase {

	Page<Order> list(Pageable pageable);
}
