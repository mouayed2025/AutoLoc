package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientDto;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.IMapper;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final IMapper mapper;

    @Override
    public ClientDto create(ClientDto clientDto) {
        if (clientDto.getIdClient() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau client ne doit pas avoir d'identifiant");
        }
        Client client = mapper.toEntity(clientDto);
        valider(client);
        return mapper.toDto(clientRepository.save(client));
    }

    @Override
    public ClientDto findById(Long id) {
        return mapper.toDto(getClient(id));
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        Client existant = getClient(id);
        Client client = mapper.toEntity(clientDto);
        valider(client);
        existant.setNom(client.getNom());
        existant.setPrenom(client.getPrenom());
        existant.setEmail(client.getEmail());
        existant.setTelephone(client.getTelephone());
        existant.setNumPermis(client.getNumPermis());
        existant.setDateInscription(client.getDateInscription());
        return mapper.toDto(clientRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client getClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    private static void valider(Client client) {
        if (estVide(client.getNom())) {
            throw new IllegalArgumentException("Le nom du client est obligatoire");
        }
        if (estVide(client.getPrenom())) {
            throw new IllegalArgumentException("Le prénom du client est obligatoire");
        }
        if (estVide(client.getEmail())) {
            throw new IllegalArgumentException("L'e-mail du client est obligatoire");
        }
    }

    private static boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}
