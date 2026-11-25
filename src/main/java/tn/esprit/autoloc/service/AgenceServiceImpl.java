package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.AgenceDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final IMapper mapper;

    @Override
    public AgenceDto create(AgenceDto agenceDto) {
        if (agenceDto.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        Agence agence = mapper.toEntity(agenceDto);
        valider(agence);
        return mapper.toDto(agenceRepository.save(agence));
    }

    @Override
    public AgenceDto findById(Long id) {
        return mapper.toDto(getAgence(id));
    }

    @Override
    public List<AgenceDto> findAll() {
        return agenceRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public AgenceDto update(Long id, AgenceDto agenceDto) {
        Agence existant = getAgence(id);
        Agence agence = mapper.toEntity(agenceDto);
        valider(agence);
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return mapper.toDto(agenceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    private Agence getAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    private static void valider(Agence agence) {
        if (estVide(agence.getNom())) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
        if (estVide(agence.getVille())) {
            throw new IllegalArgumentException("La ville de l'agence est obligatoire");
        }
        if (estVide(agence.getAdresse())) {
            throw new IllegalArgumentException("L'adresse de l'agence est obligatoire");
        }
    }

    private static boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
