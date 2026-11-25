package tn.esprit.autoloc.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.AgenceDto;
import tn.esprit.autoloc.dto.ClientDto;
import tn.esprit.autoloc.dto.ContratDto;
import tn.esprit.autoloc.dto.EmployeDto;
import tn.esprit.autoloc.dto.EquipementDto;
import tn.esprit.autoloc.dto.MaintenanceDto;
import tn.esprit.autoloc.dto.PaiementDto;
import tn.esprit.autoloc.dto.ReservationDto;
import tn.esprit.autoloc.dto.VehiculeDto;

// MapStruct génère l'implémentation de cette interface à la compilation (bean Spring).
// Les relations ne sont pas recréées dans toEntity : le service les retrouve en base à partir des identifiants.
@Mapper(componentModel = "spring")
public interface IMapper {

    @Mapping(target = "idAgence", source = "agence.idAgence")
    VehiculeDto toDto(Vehicule vehicule);

    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    Vehicule toEntity(VehiculeDto vehiculeDto);

    // Atelier6 : DTO retourné après l'affectation d'une maintenance à un véhicule.
    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    @Mapping(target = "immatriculationVehicule", source = "vehicule.immatriculation")
    MaintenanceDto toDto(Maintenance maintenance);

    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDto maintenanceDto);

    EquipementDto toDto(Equipement equipement);

    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDto equipementDto);

    AgenceDto toDto(Agence agence);

    @Mapping(target = "vehicules", ignore = true)
    @Mapping(target = "employes", ignore = true)
    Agence toEntity(AgenceDto agenceDto);

    @Mapping(target = "idAgence", source = "agence.idAgence")
    EmployeDto toDto(Employe employe);

    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDto employeDto);

    ClientDto toDto(Client client);

    @Mapping(target = "reservations", ignore = true)
    Client toEntity(ClientDto clientDto);

    @Mapping(target = "idReservation", source = "reservation.idReservation")
    ContratDto toDto(Contrat contrat);

    @Mapping(target = "reservation", ignore = true)
    Contrat toEntity(ContratDto contratDto);

    @Mapping(target = "idContrat", source = "contrat.idContrat")
    PaiementDto toDto(Paiement paiement);

    @Mapping(target = "contrat", ignore = true)
    Paiement toEntity(PaiementDto paiementDto);

    @Mapping(target = "idClient", source = "client.idClient")
    @Mapping(target = "idVehicule", source = "vehicule.idVehicule")
    ReservationDto toDto(Reservation reservation);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(target = "contrat", ignore = true)
    Reservation toEntity(ReservationDto reservationDto);

    // Composition Contrat -> Paiements : chaque paiement doit connaître son contrat (côté propriétaire).
    @AfterMapping
    default void lierPaiements(@MappingTarget Contrat contrat) {
        if (contrat.getPaiements() != null) {
            contrat.getPaiements().forEach(paiement -> paiement.setContrat(contrat));
        }
    }
}
