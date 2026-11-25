package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.EmployeDto;

public interface IEmployeService {
    EmployeDto create(EmployeDto employeDto);
    EmployeDto findById(Long id);
    List<EmployeDto> findAll();
    EmployeDto update(Long id, EmployeDto employeDto);
    void deleteById(Long id);
}
