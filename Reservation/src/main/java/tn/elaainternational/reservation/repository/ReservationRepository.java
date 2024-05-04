package tn.elaainternational.reservation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.elaainternational.reservation.Models.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {


}
