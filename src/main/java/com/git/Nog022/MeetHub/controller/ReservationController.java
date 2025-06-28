package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.dto.ReservationDTO;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/reservations")
@Tag(name = "Reservation", description = "Meeting room management")
public class ReservationController {

    public static Logger logger = LoggerFactory.getLogger(ReservationController.class);


    @Autowired
    private ReservationService reservationService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new reservation", description = "Creates a new reservation entry in the system")
    public ReservationDTO save(@RequestBody ReservationDTO reservation) {
        logger.info("Entered the save controller");
        return reservationService.save(reservation);
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
    public void delete(@PathVariable Integer id) {
        reservationService.delete(id);
    }

    @GetMapping("/listReservations")
    @Operation(summary = "List all reservations", description = "Returns a list of all registered reservations")
    public List<Reservation> listReservations() {
        return reservationService.listReservations();
    }


}
