package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "application")
@Data
public class ApplicationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "apikey", nullable = false)
    private String apikey;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

}
