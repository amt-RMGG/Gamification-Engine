package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
public class BadgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int experienceValue;

/*    @ManyToOne
    private ApplicationEntity application;*/

}
