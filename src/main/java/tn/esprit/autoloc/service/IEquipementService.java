package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.EquipementDto;

public interface IEquipementService {
    EquipementDto create(EquipementDto equipementDto);
    EquipementDto findById(Long id);
    List<EquipementDto> findAll();
    EquipementDto update(Long id, EquipementDto equipementDto);
    void deleteById(Long id);
}
