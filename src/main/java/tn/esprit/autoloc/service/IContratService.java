package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.ContratDto;

public interface IContratService {
    ContratDto create(ContratDto contratDto);
    ContratDto findById(Long id);
    List<ContratDto> findAll();
    ContratDto update(Long id, ContratDto contratDto);
    void deleteById(Long id);
    List<ContratDto> findByIdVehicule(Long idVehicule);
}
