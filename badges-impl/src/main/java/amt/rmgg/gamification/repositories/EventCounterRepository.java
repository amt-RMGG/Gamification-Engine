package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.EventCounterEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventCounterRepository extends CrudRepository<EventCounterEntity, Long> {
}
