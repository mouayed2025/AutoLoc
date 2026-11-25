package tn.esprit.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ContratDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idContrat;

    @NotNull(message = "La date de signature est obligatoire")
    @Schema(example = "2026-11-02")
    private LocalDate dateSignature;

    @NotNull(message = "Le montant total est obligatoire")
    @PositiveOrZero(message = "Le montant total ne doit pas être négatif")
    @Digits(integer = 8, fraction = 2, message = "Le montant total doit avoir au plus 8 chiffres et 2 décimales")
    @Schema(example = "480.00")
    private BigDecimal montantTotal;

    @Schema(example = "true")
    private boolean valide;

    /** Composition : les paiements envoyés à la création sont enregistrés en cascade avec le contrat. */
    @Valid
    private List<PaiementDto> paiements = new ArrayList<>();

    @Schema(description = "Identifiant de la réservation liée (facultatif, pris en compte à la création)", example = "1")
    private Long idReservation;
}
