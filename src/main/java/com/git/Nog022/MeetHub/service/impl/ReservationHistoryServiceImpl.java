package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.entity.ReservationHistory;
import com.git.Nog022.MeetHub.repository.ReservationHistoryRepository;
import com.git.Nog022.MeetHub.service.ReservationHistoryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
@Slf4j
public class ReservationHistoryServiceImpl implements ReservationHistoryService {

    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    @Override
    public void saveReservationHistory(List<Reservation> reservations) {
        try{
            log.info("Saving reservations history");
            ReservationHistory reservationHistory = new ReservationHistory();
            reservations.forEach(reservation -> {
                reservationHistory.setReservationId(reservation.getId());
                reservationHistory.setUserId(reservation.getUser().getId());
                reservationHistory.setDate(reservation.getDate());
                reservationHistory.setPersonName(reservation.getPersonName());
                reservationHistory.setRoomId(reservation.getRoom().getId());
                reservationHistory.setStartTime(reservation.getStartTime());
                reservationHistory.setEndTime(reservation.getEndTime());
                reservationHistory.setEventDescription(reservation.getEventDescription());
                reservationHistory.setInstitutionId(reservation.getInstitution().getId());
                log.info("Saving reservation history");
                reservationHistoryRepository.save(reservationHistory);
            });
        }catch (Exception e){
            log.error("saveReservationHistory: {}",e.getMessage());
        }





    }
}
