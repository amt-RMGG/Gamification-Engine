package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
public class UserEntity implements Serializable {

    @Id
    private String username;

    @ManyToMany
    private List<BadgeEntity> badges;
}
