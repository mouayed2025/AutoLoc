package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.dto.ContratDto;
import tn.esprit.autoloc.service.IContratService;

@Tag(name = "Contrats", description = "Gestion des contrats de location")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @Operation(summary = "Lister tous les contrats")
    @ApiResponse(responseCode = "200", description = "Liste des contrats")
    @GetMapping
    public List<ContratDto> findAll() {
        return contratService.findAll();
    }

    @Operation(summary = "Consulter un contrat")

    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")


    @GetMapping("/{id}")
    public ContratDto findById(@Parameter(description = "Identifiant du contrat") @PathVariable Long id) {
        return contratService.findById(id);
    }

    @Operation(summary = "Créer un contrat (avec ses paiements éventuels)")

    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDto create(@Valid @RequestBody ContratDto contratDto) {
        return contratService.create(contratDto);
    }

    @Operation(summary = "Modifier un contrat")

    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")

    @PutMapping("/{id}")
    public ContratDto update(@Parameter(description = "Identifiant du contrat") @PathVariable Long id,
                          @Valid @RequestBody ContratDto contratDto) {
        return contratService.update(id, contratDto);
    }

    @Operation(summary = "Supprimer un contrat (et ses paiements)")

    @ApiResponse(responseCode = "200", description = "Contrat trouvé")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant du contrat") @PathVariable Long id) {
        contratService.deleteById(id);
    }

    // Atelier7
    @Operation(summary = "Lister les contrats d'un véhicule")
    @ApiResponse(responseCode = "200", description = "Liste des contrats du véhicule")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("/vehicule/{idVehicule}")
    public List<ContratDto> findByIdVehicule(
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule) {
        return contratService.findByIdVehicule(idVehicule);
    }
}
