package in.mypractice.food.ordering.domain.events.publisher;

import in.mypractice.food.ordering.domain.events.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
 void publish(T domainEvent);
}
