package br.com.archsoft.constructor.application;

import br.com.archsoft.constructor.domain.event.OrderCreatedEvent;
import br.com.archsoft.constructor.ports.out.OrderEventPublisherPort;
import org.springframework.stereotype.Component;

@Component
public class ConstructorNoOpOrderEventPublisher implements OrderEventPublisherPort {

	@Override
	public void publish(OrderCreatedEvent event) {
		// Constructor stub: real RabbitMQ publishing belongs to architecture-specific repositories.
	}
}
