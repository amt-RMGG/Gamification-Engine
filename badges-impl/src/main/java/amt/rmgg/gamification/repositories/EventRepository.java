package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.BadgeEntity;
import amt.rmgg.gamification.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
}
