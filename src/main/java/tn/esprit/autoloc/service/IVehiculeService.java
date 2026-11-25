package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.VehiculeDto;

public interface IVehiculeService {
    VehiculeDto create(VehiculeDto vehiculeDto);
    VehiculeDto findById(Long id);
    List<VehiculeDto> findAll();
    VehiculeDto update(Long id, VehiculeDto vehiculeDto);
    void deleteById(Long id);
    VehiculeDto affecterAgence(Long idVehicule, Long idAgence);
    VehiculeDto affecterEquipement(Long idVehicule, Long idEquipement);
}
