package tn.elaainternational.reservation.Controlleurs;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.elaainternational.reservation.Models.Reservation;

import tn.elaainternational.reservation.service.ReservationServiceImp;

import java.util.List;


@RestController
@RequestMapping("/api/reservations")
@CrossOrigin

public class ReservationController {

    @Autowired
    private ReservationServiceImp reservationService;

    @GetMapping("/getbyusername/{username}")
    public List<Reservation> getReservationsByUsername(@PathVariable String username) {
        return reservationService.getReservationsByUsername(username);
    }

    @GetMapping("/getall")
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/getbyid/{id}")
    public Reservation getReservationById(@PathVariable String id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/add")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<Reservation> confirmReservation(@PathVariable String id, @RequestBody String userConfirmation) {
        try {
            Reservation confirmedReservation = reservationService.confirmReservation(id, userConfirmation);
            return new ResponseEntity<>(confirmedReservation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






    // Add other endpoints as needed
}