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

    ReservationDTO save(ReservationDTO reservation);

    void delete(Integer id);

    void update(Reservation reservation);

    List<Reservation> listReservations();

    List<ListReservarionDTO> listReservationsByCompany(Long id);

    Reservation reservationById(Integer id);
}
