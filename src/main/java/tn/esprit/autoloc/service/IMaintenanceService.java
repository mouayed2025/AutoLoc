package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.MaintenanceDto;

public interface IMaintenanceService {
    MaintenanceDto create(MaintenanceDto maintenanceDto);
    MaintenanceDto findById(Long id);
    List<MaintenanceDto> findAll();
    MaintenanceDto update(Long id, MaintenanceDto maintenanceDto);
    void deleteById(Long id);
    MaintenanceDto affecterVehicule(Long idMaintenance, Long idVehicule);
}
