package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.EventEntity;
import amt.rmgg.gamification.entities.EventTypeEntity;
import amt.rmgg.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventEntity, String> {

    List<EventEntity> findByEventTypeEntityAndUserEntity(EventTypeEntity eventType, UserEntity user);
    long countEventEntitiesByUserEntityAndEventTypeEntity(UserEntity userEntity, EventTypeEntity eventType);

}
