package br.com.archsoft.constructor.ports.out;

import br.com.archsoft.constructor.domain.event.OrderCreatedEvent;

public interface OrderEventPublisherPort {

	void publish(OrderCreatedEvent event);
}
