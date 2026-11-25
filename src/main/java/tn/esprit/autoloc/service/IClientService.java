package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.ClientDto;

public interface IClientService {
    ClientDto create(ClientDto clientDto);
    ClientDto findById(Long id);
    List<ClientDto> findAll();
    ClientDto update(Long id, ClientDto clientDto);
    void deleteById(Long id);
}
