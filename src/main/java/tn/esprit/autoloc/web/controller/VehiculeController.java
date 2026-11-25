package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.dto.VehiculeDto;
import tn.esprit.autoloc.service.IVehiculeService;

@Tag(name = "Véhicules", description = "Gestion des véhicules proposés à la location")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister tous les véhicules")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules")
    @GetMapping
    public List<VehiculeDto> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Consulter un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule trouvé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @GetMapping("/{id}")
    public VehiculeDto findById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Créer un véhicule")
    @ApiResponse(responseCode = "201", description = "Véhicule créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDto create(@Valid @RequestBody VehiculeDto vehiculeDto) {
        return vehiculeService.create(vehiculeDto);
    }

    @Operation(summary = "Modifier un véhicule")
    @ApiResponse(responseCode = "200", description = "Véhicule modifié")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @PutMapping("/{id}")
    public VehiculeDto update(
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long id,
            @Valid @RequestBody VehiculeDto vehiculeDto) {
        return vehiculeService.update(id, vehiculeDto);
    }

    @Operation(summary = "Supprimer un véhicule")
    @ApiResponse(responseCode = "204", description = "Véhicule supprimé")
    @ApiResponse(responseCode = "404", description = "Véhicule introuvable")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant du véhicule") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
    @Operation(summary = "Affecter un véhicule à une agence")
    @ApiResponse(responseCode = "200", description = "Véhicule affecté à l'agence")
    @ApiResponse(responseCode = "404", description = "Véhicule ou agence introuvable")
    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public VehiculeDto affecterAgence(
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule,
            @Parameter(description = "Identifiant de l'agence") @PathVariable Long idAgence) {
        return vehiculeService.affecterAgence(idVehicule, idAgence);
    }

    @Operation(summary = "Affecter un équipement à un véhicule")
    @ApiResponse(responseCode = "200", description = "Équipement affecté au véhicule")
    @ApiResponse(responseCode = "404", description = "Véhicule ou équipement introuvable")
    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDto affecterEquipement(
            @Parameter(description = "Identifiant du véhicule") @PathVariable Long idVehicule,
            @Parameter(description = "Identifiant de l'équipement") @PathVariable Long idEquipement) {
        return vehiculeService.affecterEquipement(idVehicule, idEquipement);
    }

    // Atelier7
    @Operation(summary = "Lister les véhicules d'un contrat")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules du contrat")
    @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    @GetMapping("/contrat/{idContrat}")
    public List<VehiculeDto> findByIdContrat(
            @Parameter(description = "Identifiant du contrat") @PathVariable Long idContrat) {
        return vehiculeService.findByIdContrat(idContrat);
    }

    // Atelier7
    @Operation(summary = "Lister les véhicules d'une maintenance")
    @ApiResponse(responseCode = "200", description = "Liste des véhicules de la maintenance")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @GetMapping("/maintenance/{idMaintenance}")
    public List<VehiculeDto> findByIdMaintenance(
            @Parameter(description = "Identifiant de la maintenance") @PathVariable Long idMaintenance) {
        return vehiculeService.findByIdMaintenance(idMaintenance);
    }
}
