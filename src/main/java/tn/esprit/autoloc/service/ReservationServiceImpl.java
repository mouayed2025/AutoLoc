package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.ReservationDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    // Repositories (et non services) pour éviter une dépendance circulaire entre services.
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper mapper;

    @Override
    public ReservationDto create(ReservationDto reservationDto) {
        if (reservationDto.getIdReservation() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        Reservation reservation = mapper.toEntity(reservationDto);
        valider(reservation);
        if (reservationDto.getIdClient() != null) {
            Long idClient = reservationDto.getIdClient();
            Client client = clientRepository.findById(idClient)
                    .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
            reservation.setClient(client);
        }
        if (reservationDto.getIdVehicule() != null) {
            Long idVehicule = reservationDto.getIdVehicule();
            Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                    .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
            reservation.setVehicule(vehicule);
        }
        return mapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDto findById(Long id) {
        return mapper.toDto(getReservation(id));
    }

    @Override
    public List<ReservationDto> findAll() {
        return reservationRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ReservationDto update(Long id, ReservationDto reservationDto) {
        Reservation existant = getReservation(id);
        Reservation reservation = mapper.toEntity(reservationDto);
        valider(reservation);
        existant.setDateDebut(reservation.getDateDebut());
        existant.setDateFin(reservation.getDateFin());
        existant.setStatut(reservation.getStatut());
        return mapper.toDto(reservationRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Réservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation", id));
    }

    private static void valider(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne doit pas être antérieure à la date de début");
        }
    }
}
