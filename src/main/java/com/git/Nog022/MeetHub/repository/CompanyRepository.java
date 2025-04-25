package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.entity.Local;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCnpj(@NotNull @NotEmpty @CNPJ(message = "invalid cnpj") String cnpj);

    List<Company> findByDomain(String domain);
}
