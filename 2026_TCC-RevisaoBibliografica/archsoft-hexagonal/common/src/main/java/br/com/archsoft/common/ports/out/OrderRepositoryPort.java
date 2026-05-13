package br.com.archsoft.common.ports.out;

import br.com.archsoft.common.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {
    Order save(Order order);
    Optional<Order> findById(UUID id);
    Page<Order> findAll(Pageable pageable);
}
