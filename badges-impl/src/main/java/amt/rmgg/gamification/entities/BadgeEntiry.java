package amt.rmgg.gamification.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "badges")
@Data
public class BadgeEntiry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "experienceValue", nullable = false)
    private Integer experienceValue;

    @Column(name = "application_apikey", nullable = false)
    private String applicationApikey;

}
