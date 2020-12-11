package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.EventCountEntity;
import amt.rmgg.gamification.entities.EventTypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventCountRepository extends CrudRepository<EventCountEntity, Integer> {

    List<EventCountEntity> findByEventTypeEntityAndUserId(EventTypeEntity eventType, Integer userId);

}
