package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.dto.ViaCepResponseDTO;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.exception.ValidationException;
import com.git.Nog022.MeetHub.repository.LocalRepository;
import com.git.Nog022.MeetHub.service.LocalService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public LocalServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Local save(Local local) {
        if(verifyLocalExist(local.getAddress(), local.getCity(), local.getState())) {
            throw new ValidationException("Address, City and State are not valid");
        }
        return localRepository.save(local);
    }

    @Override
    public void delete(Integer id) {
        localRepository.findById(id).map(localId -> {
            localRepository.delete(localId);
            return localId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }

    @Override
    public void update(Local local) {
        localRepository.findById(local.getId()).map(
                localFind -> {
                    local.setId(localFind.getId());
                    localRepository.save(local);
                    return localFind;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }

    @Override
    public List<Local> listLocal() {
        return localRepository.findAll();

    }

    @Override
    public Local localById(Integer id) {
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

}
