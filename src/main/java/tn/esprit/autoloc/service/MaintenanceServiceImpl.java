package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.MaintenanceDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    // Repository (et non service) pour éviter une dépendance circulaire entre services.
    private final IVehiculeRepository vehiculeRepository;
    private final IMapper mapper;

    @Override
    public MaintenanceDto create(MaintenanceDto maintenanceDto) {
        if (maintenanceDto.getIdMaintenance() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle maintenance ne doit pas avoir d'identifiant");
        }
        Maintenance maintenance = mapper.toEntity(maintenanceDto);
        valider(maintenance);
        if (maintenanceDto.getIdVehicule() != null) {
            maintenance.setVehicule(getVehicule(maintenanceDto.getIdVehicule()));
        }
        return mapper.toDto(maintenanceRepository.save(maintenance));
    }

    @Override
    public MaintenanceDto findById(Long id) {
        return mapper.toDto(getMaintenance(id));
    }

    @Override
    public List<MaintenanceDto> findAll() {
        return maintenanceRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public MaintenanceDto update(Long id, MaintenanceDto maintenanceDto) {
        Maintenance existant = getMaintenance(id);
        Maintenance maintenance = mapper.toEntity(maintenanceDto);
        valider(maintenance);
        existant.setDateDebut(maintenance.getDateDebut());
        existant.setDateFin(maintenance.getDateFin());
        existant.setDescription(maintenance.getDescription());
        return mapper.toDto(maintenanceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MaintenanceDto affecterVehicule(Long idMaintenance, Long idVehicule) {
        Maintenance maintenance = getMaintenance(idMaintenance);
        Vehicule vehicule = getVehicule(idVehicule);
        // Maintenance est le côté propriétaire (@ManyToOne) : c'est elle qui porte la clé étrangère.
        maintenance.setVehicule(vehicule);
        // Côté inverse mis à jour pour garder les deux côtés cohérents en mémoire.
        vehicule.getMaintenances().add(maintenance);
        return mapper.toDto(maintenanceRepository.save(maintenance));
    }

    private Maintenance getMaintenance(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    private Vehicule getVehicule(Long idVehicule) {
        return vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", idVehicule));
    }

    private static void valider(Maintenance maintenance) {
        if (maintenance.getDateDebut() != null && maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne doit pas être antérieure à la date de début");
        }
    }
}
