package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.Reservation;
import com.git.Nog022.MeetHub.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/reservationById/{id}")
    public Reservation reservationById(@PathVariable Integer id){
        return reservationService.reservationById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Validated Reservation reservation){
        reservationService.update(reservation);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        reservationService.delete(id);
    }

    @GetMapping("/listReservations")
    public List<Reservation> listRoom(){
        return reservationService.listReservations();
    }


}
