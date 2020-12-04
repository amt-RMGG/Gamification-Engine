package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;
    private int threshold;

    @ManyToOne
    private BadgeEntity badge;

/*    @ManyToOne
    private ApplicationEntity application;*/

}
