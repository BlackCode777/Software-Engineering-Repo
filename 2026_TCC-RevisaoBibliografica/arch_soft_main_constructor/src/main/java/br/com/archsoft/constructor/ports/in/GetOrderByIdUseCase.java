package br.com.archsoft.constructor.ports.in;

import br.com.archsoft.constructor.domain.Order;

import java.util.UUID;

public interface GetOrderByIdUseCase {

	Order getById(UUID id);
}
