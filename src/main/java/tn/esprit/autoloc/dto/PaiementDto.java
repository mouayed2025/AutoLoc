package tn.esprit.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.ModePaiement;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaiementDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idPaiement;

    @NotNull(message = "Le montant du paiement est obligatoire")
    @PositiveOrZero(message = "Le montant du paiement ne doit pas être négatif")
    @Digits(integer = 8, fraction = 2, message = "Le montant doit avoir au plus 8 chiffres et 2 décimales")
    @Schema(example = "150.00")
    private BigDecimal montant;

    @NotNull(message = "La date du paiement est obligatoire")
    @Schema(example = "2026-11-02")
    private LocalDate datePaiement;

    @NotNull(message = "Le mode de paiement est obligatoire")
    @Schema(example = "CARTE")
    private ModePaiement modePaiement;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Contrat auquel appartient le paiement")
    private Long idContrat;
}
