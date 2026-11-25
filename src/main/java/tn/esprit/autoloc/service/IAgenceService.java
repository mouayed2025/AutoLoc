package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.AgenceDto;

public interface IAgenceService {
    AgenceDto create(AgenceDto agenceDto);
    AgenceDto findById(Long id);
    List<AgenceDto> findAll();
    AgenceDto update(Long id, AgenceDto agenceDto);
    void deleteById(Long id);
}
