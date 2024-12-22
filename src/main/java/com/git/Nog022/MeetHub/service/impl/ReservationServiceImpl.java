package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.controller.ReservationController;
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
    public Reservation save(Reservation reservation) {
        logger.info("Service Reservation Save");
        logger.info(reservation.toString());

        if(roomRepository != null && roomService != null && reservationRepository != null ) {
            logger.info("Nenhum repository ou service null");
            if (checkReservation(reservation) ) {
                Room room = roomService.roomById(reservation.getRoom().getId());
                room.getReservations().add(reservation);
                room.setLastReservationId(reservation.getId());
                roomService.save(room);
                return reservationRepository.save(reservation);
            }
        }


        throw new ReservationConflictException("Unable to make reservation: schedule conflict.");

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Reservation reservation) {

    }

    @Override
    public List<Reservation> listLocal() {
        return List.of();
    }

    @Override
    public Reservation reservationById(Integer id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not find"));
    }


    private boolean checkReservation(Reservation reservation) {
        logger.info("Service Reservation Check");


       return roomRepository.findById(reservation.getRoom().getId()).map(
                room -> {

                    if(room.getReservations().isEmpty()){
                        return true;

                    }
                    //se não for vazia, verificar cada reserva,
                    //primeiramente conferindo se a data é a do dia atual,

                    //TODO Refazer a logica de verificar da ultima reserva
                    //**** Pode ocorrer casos que tenha varias reserva e ter conflito pois a ultima reserva permite o cadastro de uma nova no banco de dados **

                    Reservation lastReservation = LastElement.getLastElement(room.getReservations());

                    //se não for insira a reserva que está no request
                    if(!lastReservation.getDate().equals(reservation.getDate())){
                        return true;
                    }

                    //se for do dia atual, verificar o horario que começa e termina, para saber se o horario
                    // que vamos reserva bate com horario reservado

                    if(lastReservation.getEndTime().isBefore(reservation.getStartTime()) ||
                            reservation.getStartTime().isAfter(lastReservation.getEndTime()) ){
                        return true;
                    }

                    return false;

                }).orElse(false);


    }
}
