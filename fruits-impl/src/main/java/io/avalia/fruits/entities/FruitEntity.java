package io.avalia.fruits.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class FruitEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String kind;
    private String size;
    private String colour;
    private String weight;

    @Column(columnDefinition = "DATE")
    private LocalDate expirationDate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expirationDateTime;

}
