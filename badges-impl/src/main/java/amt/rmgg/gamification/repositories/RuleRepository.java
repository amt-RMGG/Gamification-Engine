package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.api.model.EventCounter;
import amt.rmgg.gamification.entities.EventCounterEntity;
import amt.rmgg.gamification.entities.RuleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RuleRepository extends CrudRepository<RuleEntity, Long> {

    List<RuleEntity> findByEventCounter(EventCounterEntity eventCounter);

}
