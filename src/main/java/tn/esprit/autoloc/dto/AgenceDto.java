package tn.esprit.autoloc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AgenceDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long idAgence;

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Schema(example = "AutoLoc Tunis Centre")
    private String nom;

    @NotBlank(message = "La ville de l'agence est obligatoire")
    @Size(max = 50, message = "La ville ne doit pas dépasser 50 caractères")
    @Schema(example = "Tunis")
    private String ville;

    @NotBlank(message = "L'adresse de l'agence est obligatoire")
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Schema(example = "12 avenue Habib Bourguiba")
    private String adresse;

    @Size(max = 20, message = "Le téléphone ne doit pas dépasser 20 caractères")
    @Schema(example = "71000000")
    private String telephone;
}
