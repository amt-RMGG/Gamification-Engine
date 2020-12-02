package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class EventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    private long UserId;
    private String badgeRank;

}
