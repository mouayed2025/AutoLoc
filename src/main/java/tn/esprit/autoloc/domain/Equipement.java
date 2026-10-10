package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Equipement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) @EqualsAndHashCode.Include
    Long idEquipement;

    @Column(nullable = false, unique = true, length = 100)
    String libelle;

    @ManyToMany(mappedBy = "equipements", fetch = FetchType.LAZY)
    private Set<Vehicule> vehicules = new HashSet<>();
}
