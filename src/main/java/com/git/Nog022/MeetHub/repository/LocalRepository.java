package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.Local;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    Optional<Local> findByAddressAndCityAndState(String address, String city, String state);

    List<Local> findByAddress(@NotEmpty String address);

    boolean findByCity(@NotEmpty String city);

    List<Local> findByCompanyId(Long companyId);

    List<Local> id(Long id);
}
