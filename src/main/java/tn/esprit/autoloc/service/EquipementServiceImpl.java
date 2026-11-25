package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.EquipementDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final IMapper mapper;

    @Override
    public EquipementDto create(EquipementDto equipementDto) {
        if (equipementDto.getIdEquipement() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel équipement ne doit pas avoir d'identifiant");
        }
        Equipement equipement = mapper.toEntity(equipementDto);
        valider(equipement);
        return mapper.toDto(equipementRepository.save(equipement));
    }

    @Override
    public EquipementDto findById(Long id) {
        return mapper.toDto(getEquipement(id));
    }

    @Override
    public List<EquipementDto> findAll() {
        return equipementRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public EquipementDto update(Long id, EquipementDto equipementDto) {
        Equipement existant = getEquipement(id);
        Equipement equipement = mapper.toEntity(equipementDto);
        valider(equipement);
        existant.setLibelle(equipement.getLibelle());
        return mapper.toDto(equipementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Équipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement getEquipement(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement", id));
    }

    private static void valider(Equipement equipement) {
        String libelle = equipement.getLibelle();
        if (libelle == null || libelle.isBlank()) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }
    }
}
