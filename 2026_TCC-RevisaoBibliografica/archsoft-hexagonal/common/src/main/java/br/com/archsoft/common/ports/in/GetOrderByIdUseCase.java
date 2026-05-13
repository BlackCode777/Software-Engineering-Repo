package br.com.archsoft.common.ports.in;

import br.com.archsoft.common.domain.Order;
import java.util.UUID;

public interface GetOrderByIdUseCase {
    Order getById(UUID id);
}
