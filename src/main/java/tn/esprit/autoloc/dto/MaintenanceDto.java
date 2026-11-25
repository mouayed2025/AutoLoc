package tn.esprit.autoloc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MaintenanceDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idMaintenance;

    @NotNull(message = "La date de début est obligatoire")
    @Schema(example = "2026-11-20")
    private LocalDate dateDebut;

    @Schema(example = "2026-11-25")
    private LocalDate dateFin;

    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Schema(example = "Vidange et changement des plaquettes")
    private String description;

    @Schema(description = "Identifiant du véhicule (facultatif, pris en compte à la création)", example = "1")
    private Long idVehicule;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Immatriculation du véhicule affecté")
    private String immatriculationVehicule;

    /** Contrôle sur deux champs : la date de fin ne peut pas précéder la date de début. */
    @JsonIgnore
    @AssertTrue(message = "La date de fin ne doit pas être antérieure à la date de début")
    public boolean isDatesCoherentes() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
