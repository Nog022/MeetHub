package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.entity.ReservationHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationHistoryService {

    void saveReservationHistory(List<Reservation> reservations);
}
