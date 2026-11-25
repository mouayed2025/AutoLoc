package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.ContratDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IReservationRepository reservationRepository;
    private final IMapper mapper;

    @Override
    public ContratDto create(ContratDto contratDto) {
        if (contratDto.getIdContrat() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        // Le mapper rattache les paiements au contrat : ils sont enregistrés en cascade (ALL).
        Contrat contrat = mapper.toEntity(contratDto);
        valider(contrat);
        if (contratDto.getIdReservation() != null) {
            Long idReservation = contratDto.getIdReservation();
            Reservation reservation = reservationRepository.findById(idReservation)
                    .orElseThrow(() -> new ResourceNotFoundException("Réservation", idReservation));
            contrat.setReservation(reservation);
        }
        return mapper.toDto(contratRepository.save(contrat));
    }

    @Override
    public ContratDto findById(Long id) {
        return mapper.toDto(getContrat(id));
    }

    @Override
    public List<ContratDto> findAll() {
        return contratRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ContratDto update(Long id, ContratDto contratDto) {
        Contrat existant = getContrat(id);
        Contrat contrat = mapper.toEntity(contratDto);
        valider(contrat);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        // ni l'identifiant ni la collection de paiements ne sont touchés
        return mapper.toDto(contratRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    // Atelier7 : liste des contrats d'un véhicule
    @Override
    public List<ContratDto> findByIdVehicule(Long idVehicule) {
        if (!vehiculeRepository.existsById(idVehicule)) {
            throw new ResourceNotFoundException("Véhicule", idVehicule);
        }
        return contratRepository.findByReservationVehiculeIdVehicule(idVehicule).stream()
                .map(mapper::toDto)
                .toList();
    }

    private Contrat getContrat(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    private static void valider(Contrat contrat) {
        if (contrat.getMontantTotal() == null || contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le montant total est obligatoire et ne doit pas être négatif");
        }
    }
}
