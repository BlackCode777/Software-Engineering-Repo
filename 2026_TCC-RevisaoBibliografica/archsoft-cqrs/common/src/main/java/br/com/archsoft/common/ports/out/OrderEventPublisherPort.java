package br.com.archsoft.common.ports.out;

import br.com.archsoft.common.domain.event.OrderCreatedEvent;

public interface OrderEventPublisherPort {
    void publish(OrderCreatedEvent event);
}
