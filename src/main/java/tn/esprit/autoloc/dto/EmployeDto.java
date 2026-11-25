package tn.esprit.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.RoleEmploye;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EmployeDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idEmploye;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    @Schema(example = "Ben Salah")
    private String nom;

    @NotBlank(message = "Le prénom de l'employé est obligatoire")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    @Schema(example = "Amine")
    private String prenom;

    @NotNull(message = "Le rôle de l'employé est obligatoire")
    @Schema(example = "AGENT")
    private RoleEmploye role;

    @Schema(description = "Identifiant de l'agence (facultatif, pris en compte à la création)", example = "1")
    private Long idAgence;
}
