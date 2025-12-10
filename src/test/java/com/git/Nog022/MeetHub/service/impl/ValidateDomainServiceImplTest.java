package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.Institution;
import com.git.Nog022.MeetHub.repository.InstitutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateDomainServiceImplTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @InjectMocks
    private ValidateDomainServiceImpl validateDomainService;

    private Institution institution;

    @BeforeEach
    void setup() {

        institution = new Institution();
        institution.setId(1L);
        institution.setCnpj("123456789");
        institution.setName("UFF");
        institution.setDomains(List.of("uff.br", "id.uff.br"));
    }


    @Test
    void validateDomainDomainFound() {

        String email = "aluno@uff.br";

        when(institutionRepository.findByDomains("uff.br"))
                .thenReturn(Optional.of(institution));

        Optional<Institution> result = validateDomainService.validateDomain(email);

        assertTrue(result.isPresent());
        assertEquals("UFF", result.get().getName());

        verify(institutionRepository).findByDomains("uff.br");
    }


    @Test
    void validateDomainDomainNotFound() {

        String email = "alguem@gmail.com";

        when(institutionRepository.findByDomains("gmail.com"))
                .thenReturn(Optional.empty());

        Optional<Institution> result = validateDomainService.validateDomain(email);

        assertFalse(result.isPresent());
        verify(institutionRepository).findByDomains("gmail.com");
    }
}