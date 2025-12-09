package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.config.TokenService;
import com.git.Nog022.MeetHub.dto.InstitutionDTO;
import com.git.Nog022.MeetHub.dto.InstitutionResponseDTO;
import com.git.Nog022.MeetHub.entity.Institution;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.exception.ValidationException;
import com.git.Nog022.MeetHub.repository.InstitutionRepository;
import com.git.Nog022.MeetHub.service.UserService;
import com.git.Nog022.MeetHub.service.ValidateDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceImplTest {

    @InjectMocks
    private InstitutionServiceImpl institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private UserService userService;

    @Mock
    private ValidateDomainService validateDomainService;

    @Mock
    private TokenService tokenService;

    private User user;
    private Institution institution;
    private InstitutionDTO institutionDTO;
    private InstitutionDTO updateDTO;

    @BeforeEach
    void setUp() {


        user = new User();
        user.setId(1L);
        user.setEmail("rodrigo@gmail.com");
        user.setName("Rodrigo");

        institution = new Institution();
        institution.setId(10L);
        institution.setName("UFF");
        institution.setCnpj("123456789");
        institution.setDomains(new ArrayList<>());
        institution.setUsers(new ArrayList<>());

        institutionDTO = new InstitutionDTO(
                10L,
                "123456789",
                "UFF",
                List.of("uff.br"),
                "rodrigo@gmail.com"
        );

        updateDTO = new InstitutionDTO(
                1L,
                "123456789",
                "Novo Nome",
                List.of("novo.com", "uff.br"),
                null
        );
    }

    @Test
    void saveSuccess() {

        when(userService.findByEmail("rodrigo@gmail.com")).thenReturn(user);
        when(institutionRepository.findByCnpj("123456789")).thenReturn(null);

        when(institutionRepository.save(any(Institution.class)))
                .thenAnswer(invocation -> {
                    Institution inst = invocation.getArgument(0);
                    inst.setId(10L);
                    return inst;
                });

        when(userService.save(any(User.class))).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("TOKEN123");

        InstitutionResponseDTO response = institutionService.save(institutionDTO);

        assertNotNull(response);
        assertEquals("123456789", response.cnpj());
        assertEquals("UFF", response.name());
        assertEquals("TOKEN123", response.token());

        verify(userService).findByEmail("rodrigo@gmail.com");
        verify(institutionRepository).findByCnpj("123456789");
        verify(institutionRepository).save(any(Institution.class));
        verify(userService).save(any(User.class));
    }


    @Test
    void saveCnpjAlreadyExists() {

        when(userService.findByEmail("rodrigo@gmail.com")).thenReturn(user);
        when(institutionRepository.findByCnpj("123456789")).thenReturn(institution);

        assertThrows(ValidationException.class, () ->
                institutionService.save(institutionDTO)
        );

        verify(institutionRepository, never()).save(any());
    }


    @Test
    void saveUserDoesNotExist() {
        when(userService.findByEmail("rodrigo@gmail.com")).thenReturn(null);

        assertThrows(ValidationException.class, () ->
                institutionService.save(institutionDTO)
        );
    }


    @Test
    void findByIdSuccess() {
        when(institutionRepository.findById(10L)).thenReturn(Optional.of(institution));

        Institution response = institutionService.findById(10L);

        assertNotNull(response);
        assertEquals("UFF", response.getName());
    }

    @Test
    void findByIdNotFound() {
        when(institutionRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> institutionService.findById(10L));
    }


    @Test
    void joinInstitutionIdWithDomainSuccess() {

        when(validateDomainService.validateDomain("rodrigo@gmail.com"))
                .thenReturn(Optional.of(institution));

        when(userService.save(user)).thenReturn(user);

        institutionService.joinInstitutionIdWithDomain(user);

        verify(validateDomainService).validateDomain("rodrigo@gmail.com");
        verify(userService).save(user);
        verify(institutionRepository).save(institution);

        assertEquals(institution, user.getInstitution());
        assertTrue(institution.getUsers().contains(user));
    }

    @Test
    void joinInstitutionIdWithDomainNotFound() {

        when(validateDomainService.validateDomain("rodrigo@gmail.com"))
                .thenReturn(Optional.empty());

        institutionService.joinInstitutionIdWithDomain(user);

        verify(institutionRepository, never()).save(any());
    }

    @Test
    void updateSuccess() {


        when(institutionRepository.findByCnpj(updateDTO.cnpj())).thenReturn(institution);


        InstitutionDTO result = institutionService.update(updateDTO);


        assertEquals("Novo Nome", result.name());
        assertEquals("123456789", result.cnpj());
        assertEquals(List.of("novo.com", "uff.br"), result.domain());


        assertEquals("Novo Nome", institution.getName());
        assertEquals(List.of("novo.com", "uff.br"), institution.getDomains());


        verify(institutionRepository).save(institution);
    }



}