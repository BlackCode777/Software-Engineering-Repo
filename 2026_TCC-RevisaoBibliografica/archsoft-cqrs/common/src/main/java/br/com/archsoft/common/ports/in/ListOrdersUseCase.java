package br.com.archsoft.common.ports.in;

import br.com.archsoft.common.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListOrdersUseCase {
    Page<Order> list(Pageable pageable);
}
