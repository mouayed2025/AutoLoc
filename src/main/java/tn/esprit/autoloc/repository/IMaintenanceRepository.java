package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Maintenance;

public interface IMaintenanceRepository extends JpaRepository<Maintenance, Long> {

    // Atelier7 : maintenances d'un véhicule.
    // Mots-clés : Maintenance.vehicule.idVehicule
    List<Maintenance> findByVehiculeIdVehicule(Long idVehicule);
}
