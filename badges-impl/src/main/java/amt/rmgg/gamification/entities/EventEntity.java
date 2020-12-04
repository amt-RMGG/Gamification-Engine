package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String eventName;
    private String description;

    private long UserId;

    @ManyToOne
    private EventTypeEntity eventType;

}
