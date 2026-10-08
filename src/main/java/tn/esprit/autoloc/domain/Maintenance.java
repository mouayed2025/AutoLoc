package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString(callSuper = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@AttributeOverride(name = "id", column = @Column(name = "id_maintenance"))
public class Maintenance extends BaseEntity {

    @Column(nullable = false)
    LocalDate dateDebut;

    LocalDate dateFin; // nulle tant que la maintenance est en cours

    @Column(length = 500)
    String description;
}