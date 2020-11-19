package amt.rmgg.gamification.repositories;

import amt.rmgg.gamification.entities.BadgeEntiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BadgeRepository extends JpaRepository<BadgeEntiry, String>, JpaSpecificationExecutor<BadgeEntiry> {

}