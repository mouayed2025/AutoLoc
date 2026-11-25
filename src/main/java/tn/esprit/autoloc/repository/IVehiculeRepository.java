package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.autoloc.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Atelier7 : véhicules d'un contrat.
    // Vehicule ne connaît pas Contrat : on part du contrat et on remonte Contrat -> Reservation -> Vehicule (JPQL).
    @Query("SELECT r.vehicule FROM Contrat c JOIN c.reservation r WHERE c.idContrat = :idContrat")
    List<Vehicule> findVehiculesByIdContrat(@Param("idContrat") Long idContrat);

    // Atelier7 : véhicules d'une maintenance.
    // Mots-clés : Vehicule.maintenances.idMaintenance
    List<Vehicule> findByMaintenancesIdMaintenance(Long idMaintenance);
}
