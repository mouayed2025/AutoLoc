package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.ReservationDto;

public interface IReservationService {
    ReservationDto create(ReservationDto reservationDto);
    ReservationDto findById(Long id);
    List<ReservationDto> findAll();
    ReservationDto update(Long id, ReservationDto reservationDto);
    void deleteById(Long id);
}
