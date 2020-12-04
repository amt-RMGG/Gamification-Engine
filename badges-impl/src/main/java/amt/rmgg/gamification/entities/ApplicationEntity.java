package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany
    private List<RuleEntity> rules;

    @OneToMany
    private List<EventEntity> eventTypes;
}
