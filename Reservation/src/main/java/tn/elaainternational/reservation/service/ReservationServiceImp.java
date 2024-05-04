package tn.elaainternational.reservation.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.elaainternational.reservation.Models.ContratAssurance;
import tn.elaainternational.reservation.Models.Reservation;
import tn.elaainternational.reservation.Models.User;
import tn.elaainternational.reservation.repository.ContratAssuranceRepository;
import tn.elaainternational.reservation.repository.ReservationRepository;
import tn.elaainternational.reservation.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReservationServiceImp {

    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContratAssuranceRepository contratAssuranceRepository;

    @Autowired
    private ReservationRepository reservationRepository;
    Logger logger = LoggerFactory.getLogger(getClass());


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public Reservation createReservation(Reservation reservation) {
        reservation.setReservationDateTime(LocalDateTime.now());
        reservation.setIsConfirmed("Not Confirmed");

        Optional<User> user = userRepository.findByUsername(reservation.getUserName());

        if (user.isPresent()) {
            //emailService.sendReservationConfirmationMail(user.get().getEmail(), reservation.getServiceName(), LocalDateTime.now());
            logger.warn("send mail success");

        } else {
            logger.warn("User not found for username: {}", reservation.getUserName());
        }

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


    public Reservation confirmReservation(String id, String userConfirmation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setIsConfirmed("Confirmed");
            reservation.setUserConfirmation(userConfirmation);
            reservationRepository.save(reservation);

            Optional<User> user = userRepository.findByUsername(reservation.getUserName());
            Optional<ContratAssurance> contratAssuranceOptional = contratAssuranceRepository.findByAdressemail(user.get().getEmail());

            if (contratAssuranceOptional.isPresent()) {
                ContratAssurance contratAssurance = contratAssuranceOptional.get();
                String nombreDeclarations = contratAssurance.getNombreDeclarations();

                // Split the nombreDeclarations string to get the current count and total
                String[] parts = nombreDeclarations.split("/");
                int currentCount = Integer.parseInt(parts[0]);
                int totalCount = Integer.parseInt(parts[1]);

                if (currentCount < totalCount) {
                    currentCount++; // Increment current count
                    contratAssurance.setNombreDeclarations(currentCount + "/" + totalCount);
                    contratAssuranceRepository.save(contratAssurance);
                }
            }

            return reservation;
        } else {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
    }





}

