package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@ToString @EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contrat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) @EqualsAndHashCode.Include
    Long idContrat;

    @Column(nullable = false)
    LocalDate dateSignature;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal montantTotal;

    boolean valide;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Paiement> paiements = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Reservation reservation;
}