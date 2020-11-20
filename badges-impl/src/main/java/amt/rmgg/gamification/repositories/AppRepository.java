package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.api.model.Application;
import amt.rmgg.gamification.entities.ApplicationEntity;
import amt.rmgg.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

public interface AppRepository extends CrudRepository<ApplicationEntity, String> {

}
