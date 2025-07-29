package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.ListReservarionDTO;
import com.git.Nog022.MeetHub.dto.ReservationDTO;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.Reservation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface ReservationService {

    ReservationDTO save(ReservationDTO reservation, Long userId);

    void delete(Long userId, Integer id, String role);

    void update(Reservation reservation);

    List<Reservation> listReservations();

    List<ListReservarionDTO> listReservationsByInstitution(Long id);

    Reservation reservationById(Integer id);
}
