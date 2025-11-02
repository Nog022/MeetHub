package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.controller.ReservationController;
import com.git.Nog022.MeetHub.dto.*;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.entity.Room;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.exception.ReservationConflictException;
import com.git.Nog022.MeetHub.repository.ReservationRepository;
import com.git.Nog022.MeetHub.repository.RoomRepository;

import com.git.Nog022.MeetHub.service.InstitutionService;
import com.git.Nog022.MeetHub.service.ReservationService;
import com.git.Nog022.MeetHub.service.RoomService;
import com.git.Nog022.MeetHub.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    public static Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);



    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private InstitutionService institutionService;

    @Autowired
    private UserService userService;



    @Override
    public ReservationDTO save(ReservationDTO dto, Long userId) {
        logger.info("Service Reservation Save");
        logger.info("dto reservation: {}", dto);


        if (checkReservation(dto) ) {
            Room room = roomRepository.findById(dto.roomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));

            Reservation reservation = new Reservation();
            reservation.setPersonName(dto.personName());
            reservation.setRoom(room);
            reservation.setDate(dto.date());
            reservation.setStartTime(dto.startTime());
            reservation.setEndTime(dto.endTime());
            reservation.setEventDescription(dto.eventDescription());
            reservation.setInstitution(institutionService.findById(dto.institutionId()));
            reservation.setUser(userService.findById(userId));
            reservationRepository.save(reservation);

            room.getReservations().add(reservation);
            room.setLastReservationId(reservation.getId());
            roomRepository.save(room);


            return dto;
        }



        throw new ReservationConflictException("Unable to make reservation: schedule conflict.");

    }

    @Override
    public void delete(Long userId, Integer id, String role) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not foud reservation"));

        boolean isAdmin = "ADMIN".equalsIgnoreCase(role);

        if (isAdmin || reservation.getUser().getId().equals(userId)) {
            reservationRepository.delete(reservation);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not permitted to delete reservation");
        }


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
    public List<ListReservarionDTO> listReservationsByInstitution(Long id, LocalDate date, Integer capacity, String roomName, String localName) {
        logger.info("Service Reservation List");
        logger.info("id reservation: {}", id);
        logger.info("date reservation: {}", date);
        logger.info("capacity reservation: {}", capacity);
        logger.info("room reservation: {}", roomName);
        logger.info("local reservation: {}", localName);
        List<Reservation> listReservation =  reservationRepository.reservationByFilter(id,date,capacity,roomName,localName);
        List<ListReservarionDTO> listReservarionDTO = new ArrayList<>();
        for (Reservation reservation : listReservation) {
            RoomReportDTO roomReportDTO = toResponseRoomReportDTO(reservation.getRoom());
            UserDTO userDTO = toResponseUserDTO(reservation.getUser());

            ListReservarionDTO listReservario = new ListReservarionDTO(
                    reservation.getId(),
                    reservation.getPersonName(),
                    roomReportDTO,
                    reservation.getDate(),
                    reservation.getStartTime(),
                    reservation.getEndTime(),
                    reservation.getEventDescription(),
                    userDTO
            );
            listReservarionDTO.add(listReservario);

        }

        return listReservarionDTO;
    }

    private RoomReportDTO toResponseRoomReportDTO(Room room) {
        return new RoomReportDTO(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                toResponseLocalDTO(room.getLocal()),
                room.getResources()
        );
    }


    private LocalDTO toResponseLocalDTO(Local local) {
        return new LocalDTO(
                local.getId(),
                local.getName(),
                local.getCep(),
                local.getAddress(),
                local.getNeighborhood(),
                local.getCity(),
                local.getState(),
                local.getNumber(),
                local.getComplement(),
                local.getInstitution().getId()
        );
    }

    private UserDTO toResponseUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getRole()
        );
    }



    @Override
    public Reservation reservationById(Integer id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not find"));
    }

    @Override
    public ResponseEntity<List<ReservationDTO>> listReservationsByRoomAndDate(Long roomId, LocalDateTime date) {
        logger.info("Iniciando busca de reservas para sala ID: {} e data: {}", roomId, date);

        try {
            List<Reservation> reservations = reservationRepository.reservationByRoomAndDate(roomId, date);

            if (reservations == null || reservations.isEmpty()) {
                logger.warn("Nenhuma reserva encontrada para a sala ID: {} na data: {}", roomId, date);
                return ResponseEntity.noContent().build();
            }

            List<ReservationDTO> reservationDTOs = toResponseReservationDTOList(reservations);
            logger.info("Foram encontradas {} reservas.", reservationDTOs.size());

            return ResponseEntity.ok(reservationDTOs);

        } catch (Exception e) {
            logger.error("Erro ao buscar reservas para a sala ID: {} na data: {}. Detalhes: {}", roomId, date, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }



    private List<ReservationDTO> toResponseReservationDTOList(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> new ReservationDTO(
                        reservation.getPersonName(),
                        reservation.getRoom().getId(),
                        reservation.getDate(),
                        reservation.getStartTime(),
                        reservation.getEndTime(),
                        reservation.getEventDescription(),
                        reservation.getInstitution().getId()
                ))
                .toList();
    }


    private boolean checkReservation(ReservationDTO reservation) {
        return roomRepository.findById(reservation.roomId()).map(room -> {
            if (room.getReservations().isEmpty()) {
                return true;
            }
            
            for (Reservation existingReservation : room.getReservations()) {
                if (existingReservation.getDate().equals(reservation.date())) {

                    boolean semConflito = !reservation.endTime().isAfter(existingReservation.getStartTime()) ||
                            !reservation.startTime().isBefore(existingReservation.getEndTime());

                    if (!semConflito) {
                        logger.warn("Conflict found with existing reservation!");
                        return false;
                    } else {
                        logger.info("without conflict with this reservation.");
                    }

                } else {
                    logger.info("Different dates - no conflict.");
                }
            }

            logger.info("No conflicts found - reservation allowed.");
            return true;
        }).orElseGet(() -> {
            logger.warn("Room with ID {} not found!", reservation.roomId());
            return false;
        });
    }

}
