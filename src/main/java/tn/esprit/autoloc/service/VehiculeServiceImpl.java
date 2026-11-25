package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.VehiculeDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final IMapper mapper;

    @Override
    public VehiculeDto create(VehiculeDto vehiculeDto) {
        if (vehiculeDto.getIdVehicule() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        Vehicule vehicule = mapper.toEntity(vehiculeDto);
        valider(vehicule);
        if (vehiculeDto.getIdAgence() != null) {
            vehicule.setAgence(getAgence(vehiculeDto.getIdAgence()));
        }
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeDto findById(Long id) {
        return mapper.toDto(getVehicule(id));
    }

    @Override
    public List<VehiculeDto> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public VehiculeDto update(Long id, VehiculeDto vehiculeDto) {
        Vehicule existant = getVehicule(id);
        Vehicule vehicule = mapper.toEntity(vehiculeDto);
        valider(vehicule);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return mapper.toDto(vehiculeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Véhicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public VehiculeDto affecterAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getVehicule(idVehicule);
        vehicule.setAgence(getAgence(idAgence));
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional
    public VehiculeDto affecterEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getVehicule(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement", idEquipement));
        // Vehicule est le côté propriétaire (@JoinTable) : c'est lui qui écrit dans vehicule_equipement.
        vehicule.getEquipements().add(equipement);
        // Côté inverse mis à jour pour garder les deux côtés cohérents en mémoire.
        equipement.getVehicules().add(vehicule);
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    private Vehicule getVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", id));
    }

    private Agence getAgence(Long idAgence) {
        return agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
    }

    private static void valider(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() == null || vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier est obligatoire et ne doit pas être négatif");
        }
    }
}
