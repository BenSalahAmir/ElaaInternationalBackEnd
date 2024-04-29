package tn.elaainternational.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.elaainternational.reservation.Models.Reservation;
import tn.elaainternational.reservation.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImp {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public Reservation createReservation(Reservation reservation) {
        reservation.setReservationDateTime(LocalDateTime.now());
        reservation.setConfirmed(false);
        return reservationRepository.save(reservation);
    }
/*
    public List<Reservation> getReservationsByUserId(String userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByServiceId(String serviceId) {
        return reservationRepository.findByServiceId(serviceId);
    }

    public List<Reservation> getConfirmedReservations() {
        return reservationRepository.findByIsConfirmed(true);
    }

    public List<Reservation> getUnconfirmedReservations() {
        return reservationRepository.findByIsConfirmed(false);
    }
*/
}

