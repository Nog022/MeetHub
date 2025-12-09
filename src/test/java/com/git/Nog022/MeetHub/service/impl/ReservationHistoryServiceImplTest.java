package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.*;
import com.git.Nog022.MeetHub.repository.ReservationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationHistoryServiceImplTest {


    @Mock
    private ReservationHistoryRepository reservationHistoryRepository;

    @InjectMocks
    private ReservationHistoryServiceImpl reservationHistoryService;

    private List<Reservation> reservations;
    private Reservation reservation1;
    private Reservation reservation2;
    private User user;
    private Room room;
    private Institution institution;

    @BeforeEach
    void setup() {

        user = new User();
        user.setId(10L);

        room = new Room();
        room.setId(20L);

        institution = new Institution();
        institution.setId(30L);

        reservation1 = new Reservation();
        reservation1.setId(1);
        reservation1.setUser(user);
        reservation1.setRoom(room);
        reservation1.setInstitution(institution);
        reservation1.setDate(LocalDateTime.now());
        reservation1.setStartTime(LocalTime.of(10, 0));
        reservation1.setEndTime(LocalTime.of(12, 0));
        reservation1.setPersonName("Rodrigo");
        reservation1.setEventDescription("Reunião");

        reservation2 = new Reservation();
        reservation2.setId(2);
        reservation2.setUser(user);
        reservation2.setRoom(room);
        reservation2.setInstitution(institution);
        reservation2.setDate(LocalDateTime.now());
        reservation2.setStartTime(LocalTime.of(14, 0));
        reservation2.setEndTime(LocalTime.of(15, 0));
        reservation2.setPersonName("Lucas");
        reservation2.setEventDescription("Apresentação");

        reservations = Arrays.asList(reservation1, reservation2);
    }

    @Test
    void saveReservationHistorySuccess() {

        reservationHistoryService.saveReservationHistory(reservations);
        verify(reservationHistoryRepository, times(2)).save(any(ReservationHistory.class));
    }

    @Test
    void saveReservationHistoryEmptyListSavesNothing() {

        reservationHistoryService.saveReservationHistory(Collections.emptyList());
        verify(reservationHistoryRepository, never()).save(any());
    }

    @Test
    void saveReservationHistoryExceptionDoesNotThrow() {


        doThrow(new RuntimeException("Erro"))
                .when(reservationHistoryRepository)
                .save(any());

        assertDoesNotThrow(() -> reservationHistoryService.saveReservationHistory(reservations));
    }

}