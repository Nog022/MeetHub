package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.ViaCepResponseDTO;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.exception.ValidationException;
import com.git.Nog022.MeetHub.repository.LocalRepository;
import com.git.Nog022.MeetHub.service.InstitutionService;
import com.git.Nog022.MeetHub.service.LocalService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class LocalServiceImpl implements LocalService {

    @Autowired
    private LocalRepository localRepository;


    private final RestTemplate restTemplate;

    @Autowired
    private InstitutionService institutionService;


    public LocalServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public ResponseEntity<LocalDTO> save(LocalDTO dto) {
        log.info("Saving local {}", dto);
        if(verifyLocalExist(dto.address(), dto.city(), dto.state())) {
            throw new ValidationException("Address, City and State are not valid");
        }
        Local local = new Local();
        local.setName(dto.name());
        local.setCep(dto.cep());
        local.setAddress(dto.address());
        local.setNeighborhood(dto.neighborhood());
        local.setCity(dto.city());
        local.setState(dto.state());
        local.setNumber(dto.number());
        local.setComplement(dto.complement());

        local.setInstitution(institutionService.findById(dto.institutionId()));
        localRepository.save(local);
        return ResponseEntity.ok(dto);
    }

    @Override
    public void delete(Long id) {
        localRepository.findById(id).map(localId -> {
            localRepository.delete(localId);
            return localId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }

    @Override
    public void update(LocalDTO dto) {
        Local local = localRepository.findById(dto.id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

        if(local != null) {
            local.setName(dto.name());
            local.setCep(dto.cep());
            local.setAddress(dto.address());
            local.setNeighborhood(dto.neighborhood());
            local.setCity(dto.city());
            local.setState(dto.state());
            local.setNumber(dto.number());
            local.setComplement(dto.complement());
            localRepository.save(local);
        }

    }

    @Override
    public List<Local> listLocal() {
        return localRepository.findAll();

    }

    @Override
    public Local localById(Long id) {
        return localRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }
    @Override
    public boolean verifyAddressAndCityAndState(String address, String city, String state) {
        if(localRepository.findByAddressAndCityAndState(address, city, state).isPresent()){
            return true;
        }
        return false;
    }


    private boolean verifyLocalExist(String address, String city, String state) {
        if(verifyAddressAndCityAndState(address, city, state)){
            return true;
        }
        List<Local> localsWithSameAddress = localRepository.findByAddress(address);

        for (Local local : localsWithSameAddress) {
            if (local.getCity().equalsIgnoreCase(city) && local.getState().equalsIgnoreCase(state)) {
                return true;
            }
        }

        return false;
    }

    public ViaCepResponseDTO findAddressByZipCode(String cep) {
        try {
            log.info("findAddressByZipCode()");
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            ViaCepResponseDTO response = restTemplate.getForObject(url, ViaCepResponseDTO.class);

            if (response == null || response.cep() == null || response.cep().isBlank()) {
                throw new RuntimeException("Invalid or non-existent CEP");
            }

            return response;
        } catch (Exception e) {
            log.error("Erro findAddressByZipCode: " + e.getMessage());
            throw new RuntimeException("Error while trying to fetch address from ViaCEP: " + e.getMessage(), e);
        }
    }

    @Override
    public List<LocalDTO> listLocalByInstitution(Long id) {
        List<Local> listLocal = localRepository.findByInstitutionId(id);

        return listLocal.stream().map(local -> new LocalDTO(
                local.getId(),
                local.getName(),
                local.getCep(),
                local.getAddress(),
                local.getNeighborhood(),
                local.getCity(),
                local.getState(),
                local.getNumber(),
                local.getComplement(),
                local.getInstitution().getId()
        )).toList();



    }

}
