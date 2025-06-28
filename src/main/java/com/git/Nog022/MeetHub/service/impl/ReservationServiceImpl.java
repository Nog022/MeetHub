package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.controller.ReservationController;
import com.git.Nog022.MeetHub.dto.ReservationDTO;
import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.entity.Room;
import com.git.Nog022.MeetHub.exception.ReservationConflictException;
import com.git.Nog022.MeetHub.repository.ReservationRepository;
import com.git.Nog022.MeetHub.repository.RoomRepository;
import com.git.Nog022.MeetHub.service.ReservationService;
import com.git.Nog022.MeetHub.service.RoomService;
import com.git.Nog022.MeetHub.utils.LastElement;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    public static Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public ReservationDTO save(ReservationDTO dto) {
        logger.info("Service Reservation Save");
        logger.info("dto: {}", dto);

        if(roomRepository != null && roomService != null && reservationRepository != null ) {
            if (checkReservation(dto) ) {
                Room room = roomService.roomById(dto.roomId());
                Reservation reservation = new Reservation();
                reservation.setPersonName(dto.personName());
                reservation.setRoom(room);
                reservation.setDate(dto.date());
                reservation.setStartTime(dto.startTime());
                reservation.setEndTime(dto.endTime());
                reservation.setEventDescription(dto.eventDescription());

                reservationRepository.save(reservation);

                room.getReservations().add(reservation);
                room.setLastReservationId(reservation.getId());
                roomRepository.save(room);

                return dto;
            }
        }


        throw new ReservationConflictException("Unable to make reservation: schedule conflict.");

    }

    @Override
    public void delete(Integer id) {
        reservationRepository.findById(id).map(reservationId -> {
            reservationRepository.delete(reservationId);
            return reservationId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not find"));

    }

    @Override
    public void update(Reservation reservation) {
        reservationRepository.findById(reservation.getId()).map(
                reservationFind -> {
                    reservation.setId(reservationFind.getId());
                    reservationRepository.save(reservation);
                    return reservationFind;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not find"));

    }

    @Override
    public List<Reservation> listReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation reservationById(Integer id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not find"));
    }


    private boolean checkReservation(ReservationDTO reservation) {
       return roomRepository.findById(reservation.roomId()).map(
                room -> {
                    if(room.getReservations().isEmpty()){
                        return true;
                    }
                    for (Reservation existingReservation : room.getReservations()) {
                        if(existingReservation.getDate().equals(reservation.date())){
                            if(!(existingReservation.getEndTime().isBefore(reservation.startTime()) ||
                                    reservation.startTime().isAfter(existingReservation.getEndTime())) ){
                                return false;
                            }
                        }
                    }
                    return true;
                }).orElse(false);

    }
}
