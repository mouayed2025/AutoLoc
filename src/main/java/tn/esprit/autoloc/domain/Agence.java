package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Agence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) @EqualsAndHashCode.Include
    Long idAgence;

    @Column(nullable = false, length = 100)
    String nom;

    @Column(nullable = false, length = 50)
    String ville;

    @Column(nullable = false)
    String adresse;

    @Column(length = 20)
    String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Vehicule> vehicules = new ArrayList<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes = new ArrayList<>();
}