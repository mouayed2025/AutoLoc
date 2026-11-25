package tn.esprit.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VehiculeDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idVehicule;

    @NotBlank(message = "L'immatriculation est obligatoire")
    @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
    @Schema(example = "123 TU 4567")
    private String immatriculation;

    @NotBlank(message = "La marque est obligatoire")
    @Size(max = 50, message = "La marque ne doit pas dépasser 50 caractères")
    @Schema(example = "Peugeot")
    private String marque;

    @NotBlank(message = "Le modèle est obligatoire")
    @Size(max = 50, message = "Le modèle ne doit pas dépasser 50 caractères")
    @Schema(example = "208")
    private String modele;

    @NotNull(message = "La catégorie est obligatoire")
    @Schema(example = "CITADINE")
    private CategorieVehicule categorie;

    @NotNull(message = "Le tarif journalier est obligatoire")
    @PositiveOrZero(message = "Le tarif journalier ne doit pas être négatif")
    @Digits(integer = 8, fraction = 2, message = "Le tarif journalier doit avoir au plus 8 chiffres et 2 décimales")
    @Schema(example = "120.00")
    private BigDecimal tarifJournalier;

    @NotNull(message = "Le statut est obligatoire")
    @Schema(example = "DISPONIBLE")
    private StatutVehicule statut;

    @Schema(description = "Identifiant de l'agence (facultatif, pris en compte à la création)", example = "1")
    private Long idAgence;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Équipements affectés au véhicule")
    private List<EquipementDto> equipements = new ArrayList<>();
}
