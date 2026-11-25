package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.EmployeDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final IMapper mapper;

    @Override
    public EmployeDto create(EmployeDto employeDto) {
        if (employeDto.getIdEmploye() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel employé ne doit pas avoir d'identifiant");
        }
        Employe employe = mapper.toEntity(employeDto);
        valider(employe);
        if (employeDto.getIdAgence() != null) {
            Long idAgence = employeDto.getIdAgence();
            Agence agence = agenceRepository.findById(idAgence)
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
            employe.setAgence(agence);
        }
        return mapper.toDto(employeRepository.save(employe));
    }

    @Override
    public EmployeDto findById(Long id) {
        return mapper.toDto(getEmploye(id));
    }

    @Override
    public List<EmployeDto> findAll() {
        return employeRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public EmployeDto update(Long id, EmployeDto employeDto) {
        Employe existant = getEmploye(id);
        Employe employe = mapper.toEntity(employeDto);
        valider(employe);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        return mapper.toDto(employeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé", id);
        }
        employeRepository.deleteById(id);
    }

    private Employe getEmploye(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", id));
    }

    private static void valider(Employe employe) {
        if (estVide(employe.getNom())) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire");
        }
        if (estVide(employe.getPrenom())) {
            throw new IllegalArgumentException("Le prénom de l'employé est obligatoire");
        }
        if (employe.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'employé est obligatoire");
        }
    }

    private static boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
