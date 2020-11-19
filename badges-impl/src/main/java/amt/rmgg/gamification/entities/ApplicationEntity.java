package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ApplicationEntity {

    @Id
    private String encodedUUID;

    private String name;
    private String description;
}
