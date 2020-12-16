package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
public class UserEntity implements Serializable {

    @Id
    private long id;

    @ManyToMany
    private List<BadgeEntity> badges;
}
