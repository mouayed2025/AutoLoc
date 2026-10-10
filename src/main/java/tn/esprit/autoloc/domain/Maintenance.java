package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Maintenance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) @EqualsAndHashCode.Include
    Long idMaintenance;

    @Column(nullable = false)
    LocalDate dateDebut;

    LocalDate dateFin;

    @Column(length = 500)
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicule vehicule;
}