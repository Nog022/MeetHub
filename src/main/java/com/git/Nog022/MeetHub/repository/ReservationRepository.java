package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findByCompanyId(Long companyId);
}
