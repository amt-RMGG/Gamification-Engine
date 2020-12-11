package amt.rmgg.gamification.entities;

import lombok.Data;
import lombok.Generated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class EventCountEntity implements Serializable {

    @Id
    @Generated
    private Long id;

    private Integer userId;

    private Integer count=0;

    @ManyToOne
    private EventTypeEntity eventTypeEntity;
}
