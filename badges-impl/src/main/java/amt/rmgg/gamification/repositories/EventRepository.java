package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.EventEntity;
import amt.rmgg.gamification.entities.EventCounterEntity;
import amt.rmgg.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, String> {

    List<EventEntity> findByEventCounterEntityAndUserEntity(EventCounterEntity eventCounter, UserEntity user);
    long countEventEntitiesByUserEntityAndEventCounterEntity(UserEntity userEntity, EventCounterEntity eventCounter);

}
