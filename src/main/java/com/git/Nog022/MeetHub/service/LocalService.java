package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.ViaCepResponseDTO;
import com.git.Nog022.MeetHub.entity.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface LocalService {

    ResponseEntity<LocalDTO> save(LocalDTO local);

    void delete(Integer id);

    void update(Local local);

    List<Local> listLocal   ();

    Local localById(Integer id);

    boolean verifyAddressAndCityAndState(String address, String city, String state);

    ViaCepResponseDTO findAddressByZipCode(String cep);
}
