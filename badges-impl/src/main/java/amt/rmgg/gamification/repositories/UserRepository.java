package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAnd(Long Id);
}
