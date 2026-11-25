package tn.esprit.autoloc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    // Atelier7 : contrats d'un véhicule.
    // Mots-clés : Contrat.reservation.vehicule.idVehicule
    List<Contrat> findByReservationVehiculeIdVehicule(Long idVehicule);
}
