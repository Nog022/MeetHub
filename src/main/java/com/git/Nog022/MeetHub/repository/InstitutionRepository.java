package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.Institution;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Institution findByCnpj(@NotNull @NotEmpty @CNPJ(message = "invalid cnpj") String cnpj);

    //List<Institution> findByDomain(String domain);


    Optional<Institution> findByDomains (String domain);


}
