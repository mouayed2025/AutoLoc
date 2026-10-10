package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) @EqualsAndHashCode.Include
    Long idEmploye;

    @Column(nullable = false, length = 50)
    String nom;

    @Column(nullable = false, length = 50)
    String prenom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    RoleEmploye role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Agence agence;
}