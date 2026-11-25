package tn.esprit.autoloc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.StatutReservation;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReservationDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idReservation;

    @NotNull(message = "La date de début est obligatoire")
    @Schema(example = "2026-12-01")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    @Schema(example = "2026-12-05")
    private LocalDate dateFin;

    @NotNull(message = "Le statut de la réservation est obligatoire")
    @Schema(example = "EN_ATTENTE")
    private StatutReservation statut;

    @Schema(description = "Identifiant du client (facultatif, pris en compte à la création)", example = "1")
    private Long idClient;

    @Schema(description = "Identifiant du véhicule (facultatif, pris en compte à la création)", example = "1")
    private Long idVehicule;

    /** Contrôle sur deux champs : la date de fin ne peut pas précéder la date de début. */
    @JsonIgnore
    @AssertTrue(message = "La date de fin ne doit pas être antérieure à la date de début")
    public boolean isDatesCoherentes() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
