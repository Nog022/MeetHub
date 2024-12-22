package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/reservations")
public class ReservationController {

    public static Logger logger = LoggerFactory.getLogger(ReservationController.class);


    @Autowired
    private ReservationService reservationService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation save(@RequestBody Reservation reservation) {
        logger.info("Entrou no controller save");
        return reservationService.save(reservation);
    }

}
