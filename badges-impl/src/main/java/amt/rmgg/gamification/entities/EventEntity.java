package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class EventEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date timestamp;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private EventTypeEntity eventTypeEntity;
}
