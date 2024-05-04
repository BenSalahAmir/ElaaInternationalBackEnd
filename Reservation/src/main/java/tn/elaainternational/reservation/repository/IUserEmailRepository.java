package tn.elaainternational.reservation.repository;

import tn.elaainternational.reservation.Models.UserMail;

import java.time.LocalDateTime;

public interface IUserEmailRepository {

    public void SendReservationByMail(UserMail mail);
    public void sendReservationConfirmationMail(String username, String serviceName, LocalDateTime reservationDateTime) ;


    }
