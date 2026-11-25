package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.PaiementDto;

public interface IPaiementService {
    PaiementDto findById(Long id);
    List<PaiementDto> findAll();
}
