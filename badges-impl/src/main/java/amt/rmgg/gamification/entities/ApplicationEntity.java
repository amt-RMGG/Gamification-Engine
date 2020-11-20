package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ApplicationEntity {

    @Id
    private String apikey;

    private String name;
    private String description;

}
