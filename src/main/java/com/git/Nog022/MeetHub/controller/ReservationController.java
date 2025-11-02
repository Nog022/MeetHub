package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.config.AuthenticatedUserProvider;
import com.git.Nog022.MeetHub.dto.ListReservarionDTO;
import com.git.Nog022.MeetHub.dto.ReservationDTO;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/reservations")
@Tag(name = "Reservation", description = "Meeting room management")
public class ReservationController {

    public static Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private AuthenticatedUserProvider authenticatedUserProvider;


    @Autowired
    private ReservationService reservationService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new reservation", description = "Creates a new reservation entry in the system")
    public ReservationDTO save(@RequestBody ReservationDTO reservation, HttpServletRequest request) {
        logger.info("Entered the save controller");
        Long userId = authenticatedUserProvider.getUserId(request);
        return reservationService.save(reservation, userId);
    }

    @GetMapping("/reservationById/{id}")
    @Operation(summary = "Get reservation by ID", description = "Returns the details of a reservation by its ID")
    public Reservation reservationById(@PathVariable Integer id) {
        return reservationService.reservationById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing reservation", description = "Updates the information of an existing reservation")
    public void update(@RequestBody @Validated Reservation reservation) {
        reservationService.update(reservation);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a reservation", description = "Deletes the reservation by its ID")
    public void delete(@PathVariable Integer id, HttpServletRequest request) {
        Long userId = authenticatedUserProvider.getUserId(request);
        String role = authenticatedUserProvider.getRole(request);
        reservationService.delete(userId, id, role);
    }

    @GetMapping("/listReservationsByRoomAndDate/{roomId}/{date}")
    public ResponseEntity<List<ReservationDTO>> listReservationsByRoomAndDate(
            @PathVariable Long roomId,
            @PathVariable LocalDateTime date) {


        return reservationService.listReservationsByRoomAndDate(roomId, date);
    }


    @GetMapping("/listReservations")
    @Operation(summary = "List all reservations", description = "Returns a list of all registered reservations")
    public List<Reservation> listReservations() {
        return reservationService.listReservations();
    }

    @GetMapping("/listReservationsByInstitution/{id}")
    @Operation(summary = "List all reservations by Institution id", description = "Returns a list of all registered reservations")
    public List<ListReservarionDTO> listReservationsByRoom(
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) String roomName,
            @RequestParam(required = false) String localName
            ) {
        return reservationService.listReservationsByInstitution(id,date,capacity,roomName,localName);
    }


}
