package amt.rmgg.gamification.entities;

import amt.rmgg.gamification.api.model.Badge;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ApplicationEntity {

    @Id
    private String apikey;

    private String name;
    private String description;

    @OneToMany
    private List<BadgeEntity> badges;
}
