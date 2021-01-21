package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.api.model.EventType;
import amt.rmgg.gamification.entities.EventTypeEntity;
import amt.rmgg.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {

    List<RuleEntity> findByEventType(EventTypeEntity eventType);

}
